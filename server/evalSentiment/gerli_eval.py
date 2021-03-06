from text_cnn import TextCNN
from tensorflow.contrib import learn
from gerli_config import *
from threading import Thread

import tensorflow as tf
import numpy as np
import os
import time
import datetime
import data_helpers
import csv

condition = False
testString = None
interruptFlag = False
predictResult = None

class sentimentThread(Thread):
    def __init__(self):
        pass

    def run(self):
        self.load()
        self.test()

    def load(self):
        # Load data
        print "load..."
        x_raw, y_test = data_helpers.load_data_and_labels(positive_data_file, negative_data_file)
        self.y_test = np.argmax(y_test, axis=1)

        # Map data into vocabulary
        vocab_path = os.path.join(checkpoint_dir, "vocab")
        vocab_processor = learn.preprocessing.VocabularyProcessor.restore(vocab_path)
        self.x_test = np.array(list(vocab_processor.transform(x_raw)))

    def eval(self):
        print "eval..."
        checkpoint_file = tf.train.latest_checkpoint(checkpoint_dir)
        graph = tf.Graph()
        with graph.as_default():
            session_conf = tf.ConfigProto(
              allow_soft_placement=allow_soft_placement,
              log_device_placement=log_device_placement)
            sess = tf.Session(config=session_conf)
            with sess.as_default():
                # Load the saved meta graph and restore variables
                print "checkpoint_file: ", checkpoint_file
                saver = tf.train.import_meta_graph("{}.meta".format(checkpoint_file))
                saver.restore(sess, checkpoint_file)
            
                # Get the placeholders from the graph by name
                input_x = graph.get_operation_by_name("input_x").outputs[0]
                # input_y = graph.get_operation_by_name("input_y").outputs[0]
                dropout_keep_prob = graph.get_operation_by_name("dropout_keep_prob").outputs[0]
            
                # Tensors we want to evaluate
                predictions = graph.get_operation_by_name("output/predictions").outputs[0]
            
                # Generate batches for one epoch
                batches = data_helpers.batch_iter(list(self.x_test), batch_size, 1, shuffle=False)
            
                # Collect the predictions here
                all_predictions = []
            
                for x_test_batch in batches:
                    batch_predictions = sess.run(predictions, {input_x: x_test_batch, dropout_keep_prob: 1.0})
                    all_predictions = np.concatenate([all_predictions, batch_predictions])
                print "all predict: ", all_predictions, type(all_predictions)

        # Print accuracy if y_test is defined
        if self.y_test is not None:
            correct_predictions = float(sum(all_predictions == self.y_test))
            print("Total number of test examples: {}".format(len(self.y_test)))
            print("Accuracy: {:g}".format(correct_predictions/float(len(self.y_test))))

    def test(self):
        global interruptFlag
        global condition
        global predictResult

        print "test..."
        checkpoint_file = tf.train.latest_checkpoint(checkpoint_dir)
        graph = tf.Graph()
        with graph.as_default():
            session_conf = tf.ConfigProto(
              allow_soft_placement=allow_soft_placement,
              log_device_placement=log_device_placement)
            sess = tf.Session(config=session_conf)
            with sess.as_default():
                # Restore graph
                saver = tf.train.import_meta_graph("{}.meta".format(checkpoint_file))
                saver.restore(sess, checkpoint_file)

                # Restore graph declaration
                vocab_path = os.path.join(checkpoint_dir, "vocab")
                vocab_processor = learn.preprocessing.VocabularyProcessor.restore(vocab_path)
                input_x = graph.get_operation_by_name("input_x").outputs[0]
                dropout_keep_prob = graph.get_operation_by_name("dropout_keep_prob").outputs[0]
                predictions = graph.get_operation_by_name("output/predictions").outputs[0]

                # Testing
                while True:
                    while not condition and not interruptFlag:
                        i = 0                    
                    x_raw = data_helpers.convertData(testString)
                    x_test = np.array(list(vocab_processor.transform(x_raw)))
                    batches = data_helpers.batch_iter(list(x_test), batch_size, 1, shuffle=False)
                    for x_test_batch in batches:
                        batch_predictions = sess.run(predictions, {input_x: x_test_batch, dropout_keep_prob: 1.0})
                        #print "Result: ", batch_predictions
                        predictResult = batch_predictions
                    if interruptFlag:
                        print "break"
                        break
                    condition = False

def askingThread():
    global testString
    global interruptFlag
    global condition
    global predictResult
    
    for i in range(2):
        # Tell the string
        testString = raw_input('Type the testing string: \n')

        # Do sentiment analysis
        condition = True
        while condition:
            i = 0

        # Get the result
        print "Result: ", predictResult

    interruptFlag = True
    print "done"


if __name__ == "__main__":
    _thread = sentimentThread()
    _askThread = Thread(target=askingThread)
    _askThread.start()
    _thread.run()
    
    
    
    _askThread.join()