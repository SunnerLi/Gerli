from Record import Record
import ServerPrint as sp
import numpy as np

"""
    This program define the process to judge which the type of the syntax it is
    By the similarity of verb, it would set as corresponding record format
    There're two type of sentence we would consider: assign & active
"""

# Constants
assignKeyword = "is"
activeKeyword = "spend"
similarThreshold = 0.5

# Variables
word_2_id = {"is" : 0, "spend" : 1}
wordEmbedded = np.array([[1, 0], [0, 1]])

def syntaxTypeJudge(subject, verb, _object, value):
    """
        main function

        Arg:    The subject, verb, object of the sentence,
                The value of the transection
        Ret:    The record object
    """
    sp.show("Sub: ", subject)
    sp.show("Vb: ", verb)
    sp.show("Obj: ", _object)
    sp.show("Val: ", value)
    sim = np.zeros(3)

    # Judge the type of the verb
    sim[0] = similarity(verb, assignKeyword)
    sim[1] = similarity(verb, activeKeyword)

    # Maximum likelihood estimation
    if np.max(sim) < similarThreshold:
        return None
    if sim[0] == np.max(sim):
        record = Record(subject, value)
    if sim[1] == np.max(sim):
        record = Record(_object, value)
    return record

def similarity(string1, string2):
    """
        Compute the similarity of the two verb

        Arg:    The two string
        Ret:    The similarity rate
    """
    vec1 = wordEmbedded[word_2_id[string1]]
    vec2 = wordEmbedded[word_2_id[string2]]
    return np.dot(vec1, vec2)