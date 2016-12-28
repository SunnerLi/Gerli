

# Data loading params
dev_sample_percentage = 1
#positive_data_file = "../data/rt-polaritydata/rt-polarity.pos"
#negative_data_file = "../data/rt-polaritydata/rt-polarity.neg"
positive_data_file = "../data/gerliData/transection.pos"
negative_data_file = "../data/gerliData/transection.neg"
checkpoint_dir = "vocabFolder/"

# Model Hyperparameters
embedding_dim = 128
filter_sizes = "3,4,5"
num_filters = 128
dropout_keep_prob = 0.5
l2_reg_lambda = 0.0

# Training parameters
batch_size = 64
num_epochs = 20
evaluate_every = 100
checkpoint_every = 500

# Misc Parameters
allow_soft_placement = True
log_device_placement = False

