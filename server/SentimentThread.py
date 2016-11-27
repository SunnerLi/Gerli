from tensorflow.contrib import learn
from threading import Thread
from text_cnn import TextCNN

import ServerPrint as sp
import tensorflow as tf
import data_helpers
import numpy as np
import datetime
import config
import time
import csv
import os


def sentimentThreading():
    """
        Implement the sentiment analysis in this thread
    """
    global sentiment_interruptFlag
    global sentiment_condition
    global sentiment_predictResult

    checkpoint_file = tf.train.latest_checkpoint(config.checkpoint_dir)
    graph = tf.Graph()
    with graph.as_default():
        session_conf = tf.ConfigProto(
          allow_soft_placement=config.allow_soft_placement,
          log_device_placement=config.log_device_placement)
        sess = tf.Session(config=session_conf)

        with sess.as_default():
            # Restore graph
            saver = tf.train.import_meta_graph("{}.meta".format(checkpoint_file))
            saver.restore(sess, checkpoint_file)
            sp.show("Sentiment - Finish tensorflow loading")

            # Restore graph declaration
            vocab_path = os.path.join(config.checkpoint_dir, "../vocab")
            vocab_processor = learn.preprocessing.VocabularyProcessor.restore(vocab_path)
            input_x = graph.get_operation_by_name("input_x").outputs[0]
            dropout_keep_prob = graph.get_operation_by_name("dropout_keep_prob").outputs[0]
            predictions = graph.get_operation_by_name("output/predictions").outputs[0]
            sp.show("Sentiment - Ready to accept string")

            # Testing
            while True:
                # Wait for the string input
                while not config.sentiment_condition and not config.sentiment_interruptFlag:
                    time.sleep(0.5)

                # Re-format the input string
                x_raw = data_helpers.convertData(config.sentiment_testString)
                x_test = np.array(list(vocab_processor.transform(x_raw)))
                batches = data_helpers.batch_iter(list(x_test), config.batch_size, 1, shuffle=False)

                # Work
                for x_test_batch in batches:
                    batch_predictions = sess.run(predictions, {input_x: x_test_batch, dropout_keep_prob: 1.0})
                    config.sentiment_predictResult = batch_predictions
                if config.sentiment_interruptFlag:
                    sp.show("Sentiment - Close process thread", Type=sp.war)
                    break

                # Turn control to the main thread
                config.sentiment_condition = False

def askingThread():
    """
        Let the users key their own sentence to test
        This function isn't used in official
    """
    global sentiment_testString
    global sentiment_interruptFlag
    global sentiment_condition
    global sentiment_predictResult
    
    for i in range(2):
        # Tell the string
        sentiment_testString = raw_input('Type the testing string: \n')

        # Do sentiment analysis
        sentiment_condition = True
        while sentiment_condition:
            i = 0

        # Get the result
        print "Result: ", sentiment_predictResult

    sentiment_interruptFlag = True
    print "done"

if __name__ == "__main__":
    _thread = sentimentThread()
    _askThread = Thread(target=askingThread)
    _askThread.start()
    _thread.run()