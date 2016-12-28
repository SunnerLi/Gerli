"""Train by word2vec_optimized module."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import word2vec as word2vec_optimized
import gerli_train_config
import tensorflow as tf
import os

flags = tf.app.flags
FLAGS = flags.FLAGS

# Step1. set IO
FLAGS.train_data = gerli_train_config.train_data
FLAGS.eval_data = 'questions-words.txt'
FLAGS.save_path = '/tmp/test/'

# Step2. set training parameter
FLAGS.batch_size = 5
FLAGS.min_count = 0
FLAGS.num_neg_samples = gerli_train_config.num_neg_samples
FLAGS.epochs_to_train = gerli_train_config.epochs_to_train
FLAGS.embedding_size = gerli_train_config.embedding_size

# Step3. train
word2vec_optimized.gerliTrain()