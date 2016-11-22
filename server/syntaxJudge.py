from Record import Record
import ServerPrint as sp
import numpy as np

"""
    This program define the process to judge which the type of the syntax it is
    By the similarity of verb, it would set as corresponding record format
    There're two type of sentence we would consider: assign & active
"""

# Constants
embeddedSize = 100

# Variables
word_2_id = {"is" : 0, "spend" : 1}
wordEmbedded = np.array([[1, 0], [0, 1]])

def similarity(string1, string2):
    """
        Compute the similarity of the two verb

        Arg:    The two string
        Ret:    The similarity rate
    """
    vec1 = wordEmbedded[word_2_id[string1]]
    vec2 = wordEmbedded[word_2_id[string2]]
    return np.dot(vec1, vec2) / (np.sqrt(np.sum(np.square(vec1))) * np.sqrt(np.sum(np.square(vec2))))

def load():
    """
        Load word embedded from file
    """
    global word_2_id
    global wordEmbedded
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
            for i in range(embeddedSize):
                _vec.append(float(string[i+1]))
            wordEmbedded.append(_vec)
            index += 1
    wordEmbedded = np.asarray(wordEmbedded)

load()

if __name__ == "__main__":
    print "is, was: ", similarity('is', 'was')
    print "is, get: ", similarity('is', 'get')
