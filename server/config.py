"""
    This file contain the constants that the parser and tagger might use
"""

# Constants
assignKeyword = "is"
activeKeyword = "spend"
similarThreshold = 0.3
embeddedSize = 100

# Variables
word_2_id = {"is" : 0, "spend" : 1}

# Sentiment analysis variable
sentiment_condition = False           # Flag to control if it need to do the process or not
sentiment_testString = None           # The string you should assign before the process work
sentiment_interruptFlag = False       # Flag to close the finite sentiment looping
sentiment_predictResult = None        # The result of sentiment analysis

# Data loading params
dev_sample_percentage = 1
checkpoint_dir = "evalSentiment/vocabFolder/"

# Model Hyperparameters
embedding_dim = 128
filter_sizes = "3,4,5"
num_filters = 128
dropout_keep_prob = 0.5
l2_reg_lambda = 0.0

# Training parameters
batch_size = 64
num_epochs = 200
evaluate_every = 100
checkpoint_every = 100

# Misc Parameters
allow_soft_placement = True
log_device_placement = False