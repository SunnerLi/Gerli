from syntaxJudge import *
import ServerPrint as sp
import netifaces as ni
from POSJudge import *
import socket
import json

"""
    The main function (entry) of the server
    The program would launch the network process and load the parser
    After that, it would loop waiting the connection
"""

# IP address constants
try:
    ni.ifaddresses('enp3s0')
except ValueError:
    ni.ifaddresses('wlan0')
try:
    UDP_IP = ip = ni.ifaddresses('enp3s0')[2][0]['addr']
except ValueError:
    UDP_IP = ip = ni.ifaddresses('wlan0')[2][0]['addr']
UDP_PORT = 2000
phoneIP = ""

# Input & output variables
s=""
last=""
sentenceKey = "sentence"
typeKey="type"
addrKey="address"

# Launch the socket
sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.bind ((UDP_IP, UDP_PORT))
sp.show("Finish bind with ip   >> ", UDP_IP)
sp.show("Finish bind with port >> ", UDP_PORT)

"""
    After launch the program, the following would keep accept the information
    The format of the sentence is:
    {
        "sentence"  : "(the_sentence_want_to_parse)"
        "type"      : "<sentence/control>"
    }
"""
while True:
    data, addr = sock.recvfrom(10000)
    data = json.loads(data)
    phoneIP = data[addrKey]
    sp.show("Phone IP: ", phoneIP)
    
    if data[typeKey] == "control":
        if data[sentenceKey] == "exit":
            break
    if data[typeKey] == "sentence":
        print "Sentence: ", data[sentenceKey]

        # Judge the length
        resJson = dict()
        if len(data[sentenceKey]) == 1:
            resJson["Sentence"] = data[sentenceKey]
            resJson["type"] = "sentence"
        elif len(data[sentenceKey]) == 2:
            resJson["Sentence"] = "Can you say more clearly?"
            resJson["type"] = "sentence"
        else:
            POSTagging(string=data[sentenceKey])
            if not verbLength() == 0:
                record = syntaxTypeJudge(
                    getSubject(), getVerb(), getObject(), getValue())
                resJson["sentence"] = record.serialize()
                resJson["type"] = "record"

        # Send the result back to the phone
        sockBack = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        print json.dumps(resJson)
        sockBack.sendto(json.dumps(resJson) , (phoneIP, UDP_PORT+1))
        sp.show("Send the result back")