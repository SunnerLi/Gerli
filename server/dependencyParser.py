from nltk.parse.stanford import StanfordDependencyParser
from nltk.parse.stanford import StanfordParser
from Record import ValueSubjectError
from Record import Record
from syntaxJudge import *
from config import *

import ServerPrint as sp
import numpy as np
import nltk
import os

"""
    This program is the implementation of dependency parsing.
    The parser is the standard stanford parser
"""

# Load stanford parser
model_path = "/home/sunner/nltk_data/stanford-english-corenlp-2016-01-10-models/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz"
dep_parser=StanfordParser(model_path=model_path)
sp.show("Finish stanford parser loading")

# Value judgement(should be initialized)
valueCount = 0                  # The counter that help the parser to count the number of NP we had walked through
valuePhrase = None              # The variable that store the value phrase, it would help to organize the value sentence
itemPhrase = None               # The variable that store the item phrase, it would help to organize the item sentence
subjectSentence = None          # The subject that might be used while the subject is item, it would help to organize the subject sentence
valueSentence = None            # The variable to store the value phrase list and store the value info
itemSentence = None             # The variable to store the item phrase list and store the item info

# Variables
wordEmbedded = np.array([[1, 0], [0, 1]])

def parseSentence(parseTree):
    """
        Find the node which label is 'S'

        Arg:    parseTree - The nltk tree object about the string want to parse
        Ret:    The sentence node
    """
    for node in parseTree:
        if type(node) == nltk.Tree:
            #print "label: ", node.label(), "\tword: ", node.leaves()
            if node.label() == 'S':
                return node
            return parseSentence(node)

def parseObject(parseTree):
    """
        Wrapper about parsing the object

        Arg:    parseTree - The nltk tree object about the string want to parse
    """
    global valueCount
    valueCount = 0              # initialize the NP counter
    if len(parseTree) <= 2:
        __parseObject(parseTree[1])
    else:
        __parseObject(parseTree)

def __parseObject(parseTree):
    """
        The implementation to parse the content of value and item.
        Notice, you should avoid use this function immediatly.
        This function would only be called by wrapper function.

        Arg:    parseTree - The nltk tree object about the string want to parse
    """
    global valueCount
    global valuePhrase
    global itemPhrase

    for node in parseTree:
        if type(node) == nltk.Tree:
            if node.label() == 'NP':
                sp.show("object parsing - NP:   ", str(node))
                if valueCount == 1:
                    itemPhrase = node
                    valueCount += 1
                if valueCount == 0:
                    valuePhrase = node
                    valueCount += 1
            __parseObject(node)    

def parseSpend(parseTree):
    """
        The implementation to parse the string which contain active keyword verb

        Arg:    parseTree - The nltk tree object about the string want to parse
    """
    global valuePhrase
    global itemPhrase
    global subjectSentence
    global valueSentence
    global itemSentence
    
    # Get the sentence
    sentenceNode = parseSentence(parseTree)
    
    # Get NP and VP
    nounPhrase = sentenceNode[0]
    verbPhrase = sentenceNode[1]

    # Get value phrase and item phrase
    parseObject(verbPhrase)

    # Get origin words
    try:
        subjectSentence = nounPhrase.leaves()
        valueSentence = valuePhrase.leaves()
        itemSentence = itemPhrase.leaves()
        sp.show("active parsing - subject: ", str(subjectSentence))
        sp.show("active parsing - value  : ", str(valueSentence))
        sp.show("active parsing - item   : ", str(itemSentence))

        # Restore to the sentence
        nounPhrase = subjectSentence
        subjectSentence = ""
        for word in nounPhrase:
            subjectSentence = subjectSentence + str(word) + ' '
        valuePhrase = valueSentence
        valueSentence = ""
        for word in valuePhrase:
            valueSentence = valueSentence + str(word) + ' '
        itemPhrase = itemSentence
        itemSentence = ""
        for word in itemPhrase:
            itemSentence = itemSentence + str(word) + ' '
    except AttributeError:
        sp.show("specific phrase is null: ", Type=sp.err)
        subjectSentence = "null"
        itemSentence = "null"
        valueSentence = "-1"

def parseIs(parseTree):
    """
        The implementation to parse the string which contain assign keyword verb

        Arg:    parseTree - The nltk tree object about the string want to parse
    """
    global valuePhrase
    global itemPhrase
    global subjectSentence
    global valueSentence
    global itemSentence

    # Get the sentence
    sentenceNode = parseSentence(parseTree)
    
    # Get NP and VP
    nounPhrase = sentenceNode[0]
    verbPhrase = sentenceNode[1]

    # Get origin words
    try:
        valuePhrase = verbPhrase[1]
        itemPhrase = nounPhrase
        valueSentence = valuePhrase.leaves()
        itemSentence = itemPhrase.leaves()
        sp.show("assign parsing - value  : ", str(valueSentence))
        sp.show("assign parsing - item   : ", str(itemSentence))

        # Restore the sentence
        itemPhrase = itemSentence
        itemSentence = ""
        for word in itemPhrase:
            itemSentence = itemSentence + str(word) + ' '
        valuePhrase = valueSentence
        valueSentence = ""
        for word in valuePhrase:
            valueSentence = valueSentence + str(word) + ' '
    except AttributeError:
        sp.show("specific phrase is null: ", Type=sp.err)
        subjectSentence = "null"
        itemSentence = "null"
        valueSentence = "-1"

def parse(record, string):
    """
        The main function of dependency parsing

        Arg:    record - The record object to store the result
                string - The string want to parse
        Ret:    The result record object or None
    """
    if record == None:
        sentences = list(dep_parser.raw_parse(string))
        verb = getVerb(sentences)
        sim = np.zeros(2)

        # Judge the type of the verb
        sim[0] = similarity(verb, assignKeyword)
        sim[1] = similarity(verb, activeKeyword)

        # Maximum likelihood estimation
        simString = str(sim)
        sp.show("Verb: " + verb, Type=sp.war)
        sp.show("Word vector compute, result: [is, spend] = " + simString, Type=sp.war)
        if np.max(sim) < similarThreshold:
            sp.show("The sentence might not the accountint string", Type=sp.err)
            subjectSentence = "null"
            itemSentence = "null"
            valueSentence = "-1"
            return Record(itemSentence, valueSentence)
        if sim[0] == np.max(sim):
            parseIs(sentences)
            record = Record(itemSentence, valueSentence)
        if sim[1] == np.max(sim):
            parseSpend(sentences)
            try:
                record = Record(itemSentence, valueSentence)
            except ValueSubjectError:
                record = Record(subjectSentence, itemSentence)
        record.dump()
    return record

def getVerb(parseTree):
    """
        Return the possible verb (root of the parsing tree)

        Arg:    parseTree - The nltk tree object about the string want to parse
        Ret:    The possible verb lexicon
    """
    return parseTree[0][0][1][0].leaves()[0]

if __name__ == "__main__":
    print parse("The jackets are 750 dollars").serialize()
    