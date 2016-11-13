"""Tests for word2vec_optimized module."""

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
import gerli_train_config

import os

import tensorflow as tf

#from tensorflow.models.embedding import word2vec_optimized
import word2vec as word2vec_optimized

flags = tf.app.flags

FLAGS = flags.FLAGS


class Word2VecTest(tf.test.TestCase):

  def setUp(self):
    #FLAGS.train_data = os.path.join(self.get_temp_dir() + "test-text.txt")
    FLAGS.train_data = gerli_train_config.train_data
    #FLAGS.eval_data = os.path.join(self.get_temp_dir() + "eval-text.txt")
    FLAGS.eval_data = 'questions-words.txt'
    FLAGS.save_path = self.get_temp_dir()
    #FLAGS.interactive = True
    
    #with open(FLAGS.train_data, "w") as f:
    #  f.write(
    #      """alice was beginning to get very tired of sitting by her sister on
    #      the bank, and of having nothing to do: once or twice she had peeped
    #      into the book her sister was reading, but it had no pictures or
    #      conversations in it, 'and what is the use of a book,' thought alice
    #      'without pictures or conversations?' So she was considering in her own
    #      mind (as well as she could, for the hot day made her feel very sleepy
    #      and stupid), whether the pleasure of making a daisy-chain would be
    #      worth the trouble of getting up and picking the daisies, when suddenly
    #      a White rabbit with pink eyes ran close by her.\n""")
    
    #with open(FLAGS.eval_data, "w") as f:
      #f.write("alice she rabbit once\n")
      #f.write('I I I I')

  def testWord2VecOptimized(self):
    FLAGS.batch_size = 5
    FLAGS.num_neg_samples = gerli_train_config.num_neg_samples
    FLAGS.epochs_to_train = gerli_train_config.epochs_to_train
    FLAGS.min_count = 0
    FLAGS.embedding_size = gerli_train_config.embedding_size
    word2vec_optimized.main([])


if __name__ == "__main__":
  tf.test.main()
