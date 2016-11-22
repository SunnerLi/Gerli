from nltk.parse.stanford import StanfordDependencyParser
from nltk.parse.stanford import StanfordParser
from syntaxJudge import *
from Record import Record
import numpy as np
import nltk
import os

print "Load dependency parser"
model_path = "/home/sunner/nltk_data/stanford-english-corenlp-2016-01-10-models/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz"
dep_parser=StanfordParser(model_path=model_path)
print "Load dependency parser done"

similarThreshold = 0.3

# Subject judgement
subject = None
isFindSubj = False

# Value judgement(should be initialized)
value = None
valueCount = 0
valuePhrase = None
itemPhrase = None
valueSentence = None
itemSentence = None

# Variables
word_2_id = {"is" : 0, "spend" : 1}
wordEmbedded = np.array([[1, 0], [0, 1]])

def parseSpend_Sentence(parseTree):
    for node in parseTree:
        if type(node) == nltk.Tree:
            print "label: ", node.label(), "\tword: ", node.leaves()
            if node.label() == 'S':
                return node
            return parseSpend_Sentence(node)

def parseSpend_Object(parseTree):
    global valueCount
    valueCount = 0
    if len(parseTree) <= 2:
        __parseSpend_Object(parseTree[1])
    else:
        __parseSpend_Object(parseTree)

def __parseSpend_Object(parseTree):
    global valueCount
    global valuePhrase
    global itemPhrase

    for node in parseTree:
        if type(node) == nltk.Tree:
            if node.label() == 'NP':
                print "NP:   ", node
                if valueCount == 1:
                    itemPhrase = node
                    valueCount += 1
                if valueCount == 0:
                    valuePhrase = node
                    valueCount += 1
            __parseSpend_Object(node)    

def parseSpend(parseTree):
    global valuePhrase
    global itemPhrase
    global valueSentence
    global itemSentence
    
    # Get the sentence
    sentenceNode = parseSpend_Sentence(parseTree)
    
    # Get NP and VP
    nounPhrase = sentenceNode[0]
    verbPhrase = sentenceNode[1]
    print "NP: ", nounPhrase
    print "VP: ", verbPhrase

    # Get value phrase and item phrase
    parseSpend_Object(verbPhrase)

    # Restore sentence
    subjectSentence = nounPhrase.leaves()
    valueSentence = valuePhrase.leaves()
    itemSentence = itemPhrase.leaves()
    print "subject: ", subjectSentence
    print "value  : ", valueSentence
    print "item   : ", itemSentence
    itemPhrase = itemSentence
    itemSentence = ""
    for word in itemPhrase:
        itemSentence = itemSentence + str(word) + ' '
    valuePhrase = valueSentence
    valueSentence = valuePhrase[0]

def parseIs(parseTree):
    global valueSentence
    global itemSentence

    # Get the sentence
    sentenceNode = parseSpend_Sentence(parseTree)
    
    # Get NP and VP
    nounPhrase = sentenceNode[0]
    verbPhrase = sentenceNode[1]
    print "NP: ", nounPhrase
    print "VP: ", verbPhrase

    # Restore the sentence
    valuePhrase = verbPhrase[1]
    itemPhrase = nounPhrase
    valueSentence = valuePhrase.leaves()
    itemSentence = itemPhrase.leaves()
    print "value  : ", valueSentence
    print "item   : ", itemSentence
    itemPhrase = itemSentence
    itemSentence = ""
    for word in itemPhrase:
        itemSentence = itemSentence + str(word) + ' '
    valuePhrase = valueSentence
    valueSentence = valuePhrase[0]

def dependencyParsing(string):
    sentences = list(dep_parser.raw_parse(string))
    verb = getVerb(sentences)
    sim = np.zeros(3)

    # Judge the type of the verb
    sim[0] = similarity(verb, assignKeyword)
    sim[1] = similarity(verb, activeKeyword)

    # Maximum likelihood estimation
    print sim
    if np.max(sim) < similarThreshold:
        return None
    if sim[0] == np.max(sim):
        parseIs(sentences)
        record = Record(itemSentence, valueSentence)
    if sim[1] == np.max(sim):
        parseSpend(sentences)
        record = Record(itemSentence, valueSentence)
    return record

def getVerb(parseTree):
    return parseTree[0][0][1][0].leaves()[0]

if __name__ == "__main__":
    print dependencyParsing("The jackets are 750 dollars").serialize()
    