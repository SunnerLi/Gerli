from nltk.tag.stanford import StanfordPOSTagger
from collections import OrderedDict
import ServerPrint as sp

"""
    This program would load the parser
    Moreover, it provide several function to parse the structure of the sentence
"""

# The parser .jar path
jar = "/home/sunner/nltk_data/models/stanford-postagger-full-2015-12-09/stanford-postagger.jar"
model = "/home/sunner/nltk_data/models/stanford-postagger-full-2015-12-09/models/english-bidirectional-distsim.tagger"

# Load the parser
#tagger = StanfordPOSTagger(model, jar)
tagger = StanfordPOSTagger("english-bidirectional-distsim.tagger")
sp.show("Finish POS tagger loading")

# Variable
parseString = ""
word_2_pos = OrderedDict()
__verb = ""
__subject = ""
__object = ""
__value = ""

def POSTagging(string="I love you"):
    """
        Main function to do the POS tagging

        Arg:    The string you want to parse
    """
    # POS tagging
    global parseString
    global word_2_pos
    word_2_pos = OrderedDict()
    parseString = tagger.tag(string.split())
    #print parseString
    for element in parseString:
        word = element[1].split('#')[0]
        pos = element[1].split('#')[1]
        word_2_pos[word] = pos
    print word_2_pos

    # Find each element
    findVerb()
    findSubject()
    findObject()
    filtIndirectObject()
    findValue()

def findSubject():
    """
        Find the subject of the sentence
    """
    global __subject
    __subject = ""
    for key in word_2_pos:
        if key == __verb:
            for key_ in word_2_pos:
                if not key_ == key:
                    __subject = __subject + key_ + ' '
                else:
                    break

def findVerb():
    """
        Find the verb of the sentence
    """
    # Initialize
    global __verb
    __verb = ""

    # Find the verb by 1st parser
    for keys in word_2_pos:
        if word_2_pos[keys] == "VV" or word_2_pos[keys] == "VC":
            __verb = keys
    
    # Find the verb by 2nd parser
    if len(__verb) == 0:
        sp.show("Didn't find the verb, try the second parser", Type=sp.war)
    # <Haven't implement>

def findObject():
    """
        Find the object of the sentence
    """
    global __object
    __object = ""
    hadFoundVerb = False
    for key in word_2_pos:
        if key == __verb:
            hadFoundVerb = True
        if (not key == __verb) and hadFoundVerb:
            __object = __object + key + ' '

def findValue():
    """
        Find the value of the transaction sentence
    """
    global __value
    for key in word_2_pos:
        if word_2_pos[key] == 'CD':
            __value = key

def filtIndirectObject():
    """
        Filter the proposition phrase if it has
    """
    global __object
    objList = __object.split()
    __object = ""
    hadFoundP = False
    for key in objList:
        if hadFoundP:
            __object = __object + key
        if word_2_pos[key] == 'P':
            hadFoundP = True

def verbLength():
    return len(__verb)

def getSubject():
    return __subject

def getVerb():
    return __verb

def getObject():
    return __object

def getValue():
    return __value
