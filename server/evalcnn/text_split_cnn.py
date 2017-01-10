import tensorflow as tf
import numpy as np
import logging

# Clear the log and open the log writing function
with open('split_cnn.log', 'w'):
    pass
logging.basicConfig(filename="split_cnn.log", mode='w', level=logging.DEBUG)

class TextSplitCNN(object):
    """
        A new CNN design for text classification
        The network will transfer the sentences to the word embedding first.
        Next, two process will be done.
        The one process is that use two convolution layer and relu layer,
        while the other adopt the same layer design with different stride parameter.
        At last, it will perform max-min pooling and softmax layer.
    """
    def __init__(self, sequence_length, 
        num_classes, vocab_size, embedding_size, num_sentences):

        # Define placeholder object
        self.x = tf.placeholder(tf.int32, [None, sequence_length], name='x')
        self.y = tf.placeholder(tf.float32, [None, num_classes], name='y')

        # Embedding layer
        with tf.name_scope('embedding'):
            W = tf.Variable(tf.random_uniform([vocab_size, embedding_size], -1.0, 1.0), name='H')
            self.wordVectors = tf.nn.embedding_lookup(W, self.x)
            self.wordVectors_expand = tf.expand_dims(self.wordVectors, -1)
            logging.debug("----- embedding -----")
            logging.debug(str(self.x.get_shape()))
            logging.debug(str(self.wordVectors.get_shape()))

        # Create the first process ( conv+reLu => conv+reLu )
        with tf.name_scope('proc-1'):
            # 1st conv + reLu
            conv1_shape = [2, 8, 1, num_sentences]
            W1 = tf.Variable(tf.random_uniform(conv1_shape, -1.0, 1.0), name='W1')
            B1 = tf.Variable(tf.constant(0.1, shape=[num_sentences]), name='B1')
            proc_conv1 = tf.nn.conv2d(self.wordVectors_expand, 
                W1, 
                strides=[1, 1, 8, 1],
                padding="VALID",
                name="conv1")
            proc_relu1 = tf.nn.relu(tf.nn.bias_add(proc_conv1, B1), name='relu1')
            logging.debug(" ----- proc-1 -----")
            logging.debug("kernel: " + str(W1.get_shape()))
            logging.debug("result: " + str(proc_conv1.get_shape()))

            # 2nd conv + reLu
            conv2_shape = [2, 16, num_sentences, num_sentences]
            W2 = tf.Variable(tf.random_normal(conv2_shape, stddev=0.1), name='W2')
            B2 = tf.Variable(tf.constant(0.1, shape=[num_sentences]), name='B2')
            proc_conv2 = tf.nn.conv2d(proc_relu1,
                W2,
                strides=[1, 1, 1, 1],
                padding='VALID',
                name='conv2')
            proc_relu2 = tf.nn.relu(tf.nn.bias_add(proc_conv2, B2), name='relu2')
            logging.debug("kernel: " + str(W2.get_shape()))
            logging.debug("result: " + str(proc_conv2.get_shape()))

        # Create the second process ( conv+reLu => conv+reLu )
        with tf.name_scope('proc-2'):
            # 1st conv + relu
            conv1_shape = [3, 8, 1, num_sentences]
            W1 = tf.Variable(tf.random_uniform(conv1_shape, -1.0, 1.0), name='W1')
            B1 = tf.Variable(tf.constant(0.1, shape=[num_sentences]), name='B1')
            proc_conv1 = tf.nn.conv2d(self.wordVectors_expand,
                W1,
                strides=[1, 1, 8, 1],
                padding='VALID',
                name='conv1')
            proc_relu1 = tf.nn.relu(tf.nn.bias_add(proc_conv1, B1), name='relu1')
            logging.debug(" ----- proc-2 -----")
            logging.debug("kernel: " + str(W1.get_shape()))
            logging.debug("result: " + str(proc_relu1.get_shape()))

            # 2nd conv + relu
            conv2_shape = [3, 16, num_sentences, num_sentences]
            W2 = tf.Variable(tf.random_normal(conv2_shape, stddev=0.1), name='W2')
            B2 = tf.Variable(tf.constant(0.1, shape=[num_sentences]), name='B2')
            proc_conv2 = tf.nn.conv2d(proc_relu1,
                W2,
                strides=[1, 1, 1, 1],
                padding='VALID',
                name='conv2')
            proc_relu2 = tf.nn.relu(tf.nn.bias_add(proc_conv2, B2), name='relu2')
            logging.debug("kernel: " + str(W2.get_shape()))
            logging.debug("result: " + str(proc_relu2.get_shape()))
            