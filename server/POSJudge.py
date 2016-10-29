from nltk.tag.stanford import StanfordPOSTagger
import ServerPrint as sp

# Load the parser
tagger = StanfordPOSTagger('english-bidirectional-distsim.tagger')
sp.show("Finish POS tagger loading")

# Variable
parseString = ""
_verb = ""
_subject = ""
_object = ""
_value = ""

def POSTagging(string="I love you"):
    global parseString
    parseString = tagger.tag(string.split())
    print parseString
    findVerb()
    findSubject()
    findValue()

def verbLength():
    return len(_verb)

def findVerb():
    # Initialize
    global _verb
    _verb = ""

    # Find the verb by 1st parser
    for word in parseString:
        pos = word[1].split('#')
        if pos[1] == "VV" or pos[1] == "VC":
            _verb = pos[0]
            print "verb: ", _verb
    
    # Find the verb by 2nd parser
    # <Haven't implement>

def findSubject():
    global _subject
    for i in range(len(parseString)):
        word = parseString[i][1].split('#')[0]
        if word == _verb:
            _subject = ""
            for j in range(i):
                if not j == i-1:
                    _subject = _subject + parseString[j][1].split('#')[0] + ' '
                else:
                    _subject += parseString[j][1].split('#')[0]
    print "Subject: ", _subject

def findValue():
    global _value
    for word in parseString:
        if word[1].split('#')[1] == 'CD':
            _value = word[1].split('#')[0]
    print "Value: ", _value

def getVerb():
    return _verb

def getSubject():
    return _subject

def getObject():
    return _object

def getValue():
    return _value
