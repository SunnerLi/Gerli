from nltk.tag.stanford import StanfordPOSTagger
import ServerPrint as sp

# Load the parser
tagger = StanfordPOSTagger('english-bidirectional-distsim.tagger')
sp.show("Finish POS tagger loading")

# Variable
parseString = ""
verb = ""

def POSTagging(string="I love you"):
    global parseString
    parseString = tagger.tag(string.split())
    findVerb()

def findVerb():
    global verb
    for word in parseString:
        pos = word[1].split('#')
        if pos[1] == "VV":
            verb = pos[0]
            print "verb: ", verb
