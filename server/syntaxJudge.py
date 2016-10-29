from Record import Record
import ServerPrint as sp
import numpy as np

# Constants
assignKeyword = "is"
activeKeyword = "spend"
passiveKeyword = "cost"
similarThreshold = 0.5

# Variables
word_2_id = {"is" : 0, "spend" : 1, "cost" : 2}
wordEmbedded = np.array([[1, 0, 0], [0, 1, 0], [0, 0, 1]])

def syntaxTypeJudge(subject, verb, _object, value):
    sp.show("Sub: ", subject)
    sp.show("Vb: ", verb)
    sp.show("Obj: ", _object)
    sp.show("Val: ", value)
    sim = np.zeros(3)

    # Judge the type of the verb
    sim[0] = similarity(verb, assignKeyword)
    sim[1] = similarity(verb, activeKeyword)
    sim[2] = similarity(verb, passiveKeyword)

    # Maximum likelihood estimation
    if np.max(sim) < similarThreshold:
        return None
    if sim[0] == np.max(sim):
        record = Record(subject, value)
    
    record.dump()
    

def similarity(string1, string2):
    vec1 = wordEmbedded[word_2_id[string1]]
    vec2 = wordEmbedded[word_2_id[string2]]
    return np.dot(vec1, vec2)