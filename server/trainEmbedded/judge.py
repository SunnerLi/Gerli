import numpy as np
import gerli_train_config

def similarity(string1, string2):
    """
        Compute the similarity of the two verb

        Arg:    The two string
        Ret:    The similarity rate
    """
    vec1 = wordEmbedded[word_2_id[string1]]
    vec2 = wordEmbedded[word_2_id[string2]]
    return np.dot(vec1, vec2) / (np.sqrt(np.sum(np.square(vec1))) * np.sqrt(np.sum(np.square(vec2))))

# Step1. load the word embedded
word_2_id = dict()
wordEmbedded = []
with open('embedded.txt', 'r') as f:
    index = 0
    while True:
        string = f.readline().split()
        if string == []:
            break
        word_2_id[string[0]] = index
        _vec = []
        for i in range(gerli_train_config.embedding_size):
            _vec.append(float(string[i+1]))
        wordEmbedded.append(_vec)
        index += 1
wordEmbedded = np.asarray(wordEmbedded)

# Step2. Test the word
assignWords = ['is', 'was', 'are', 'were']
activeWords = ['spend', 'spent', 'cost', 'costs', 'take', 'took', 'takes']
wholeWords = assignWords + activeWords
testPairs = []
for i in range(len(wholeWords)):
    for j in range(i, len(wholeWords)):
        testPairs.append((wholeWords[i], wholeWords[j]))

# List similarity
for pair in testPairs:
    print "< ", pair[0], " , ", pair[1], 
    for i in range(10-len(pair[0])-len(pair[1])):
        print "",
    print  " > :",
    print similarity(pair[0], pair[1])
