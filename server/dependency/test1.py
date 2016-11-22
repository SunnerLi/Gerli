from nltk.parse.stanford import StanfordDependencyParser
from nltk.tag.stanford import StanfordPOSTagger
from nltk.parse.stanford import StanfordParser
import nltk

import os

class_path = "/home/sunner/nltk_data/stanford-postagger-full-2015-12-09/stanford-postagger.jar"
model_path = "/home/sunner/nltk_data/stanford-english-corenlp-2016-01-10-models/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz"
#dep_parser=StanfordDependencyParser(class_path, model_path)

dep_parser=StanfordParser(model_path=model_path)
#sentences = dep_parser.raw_parse("I spent 200 on 2 pens")
#sentences = dep_parser.raw_parse("I who is a handsome guy spent 500 and 200 dollars on a yellow bike")
sentences = dep_parser.raw_parse("The launch was 500 dollars")
#sentences = dep_parser.raw_parse("I pay 200 for 2 pens")
sentences = list(sentences)
#print type(sentences[0])
print sentences[0]
#print ""
#print sentences[0][0]
#print ""
#print sentences[0][0][0]

# Subject judgement
subject = None
isFindSubj = False

# Value judgement(should be initialized)
value = None
valueCount = 0
valuePhrase = None
itemPhrase = None

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

def parseIs(parseTree):
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

if __name__ == "__main__":
    sentences = "I spent 200 on 2 pens"
    sentences = dep_parser.raw_parse(sentences)
    parseSpend(sentences)

    sentences = "The launch was 500 dollars"
    sentences = dep_parser.raw_parse(sentences)
    parseIs(sentences)