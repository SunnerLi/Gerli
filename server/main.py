from SentimentThread import *
from blur import *
from Record import *

import ServerPrint as sp
import dependencyParser
import netifaces as ni
import POSTagger
import config
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
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind ((UDP_IP, UDP_PORT))
sp.show("Finish bind with ip   >> ", UDP_IP)
sp.show("Finish bind with port >> ", UDP_PORT)

# Launch sentiment analysis thread
sentimentThread = Thread(target=sentimentThreading)
sentimentThread.start()

"""
    After launch the program, the following would keep accept the information
    The format of the sentence is:
    {
        "sentence"  : "(the_sentence_want_to_parse)"
        "type"      : "<sentence/control>"
    }

    After Processing, the program would send back to the server
    The format of the output is:
    {
        "sentence"  : (the_response_string)
        "record"    : (the_parsing_result_object)
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
        # The following shows the response rules:
        # 1. If the length is equal to 1 (for example: hi), then the server would response the same word back immediatly.
        # 2. If the length is equal to 2 (for example: I spend), then the server regards that the user doesn't finish his sentence.
        #    Thus it would tell the user to speak again.
        # 3. else case, the server would parse the sentence
        resJson = dict()
        print data[sentenceKey].split()
        if len(data[sentenceKey].split()) == 1:
            resJson["sentence"] = data[sentenceKey]
            resJson["record"] = Record()
        elif len(data[sentenceKey].split()) == 2:
            resJson["sentence"] = "Can you say more clearly?"
            resJson["record"] = None
        else:
            # Parse string
            record = None
            record = POSTagger.tag(record, string=data[sentenceKey])
            record = dependencyParser.parse(record, string=data[sentenceKey])
            resJson["record"] = record.serialize()

            # Sentiment analysis
            data[sentenceKey] = blur(data[sentenceKey])
            config.sentiment_testString = data[sentenceKey]
            config.sentiment_condition = True
            while config.sentiment_condition:
                i = 0
            sp.show("Sentiment Result: ", str(config.sentiment_predictResult))
            resJson["sentiment"] = str(config.sentiment_predictResult[0])

        # Send the result back to the phone
        sockBack = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        print json.dumps(resJson)
        sockBack.sendto(json.dumps(resJson) , (phoneIP, UDP_PORT+1))
        sp.show("Send the result back")

config.sentiment_interruptFlag = True
sp.show("End all process")