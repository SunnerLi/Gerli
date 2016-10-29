import ServerPrint as sp
import netifaces as ni
from POSJudge import *
import socket
import json

# IP address constants
ni.ifaddresses('enp3s0')
UDP_IP = ip = ni.ifaddresses('enp3s0')[2][0]['addr']
UDP_PORT = 2000

# Input & output variables
s=""
last=""
sentenceKey = "sentence"
typeKey="type"

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
    
    if data[typeKey] == "control":
        if data[sentenceKey] == "exit":
            break
    if data[typeKey] == "sentence":
        print "Sentence: ", data[sentenceKey]
        #POSTagging(data[sentenceKey])
        POSTagging()