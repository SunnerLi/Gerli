import socket
import json

UDP_IP = "192.168.0.101"
UDP_PORT = 2000

sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
sock.bind ((UDP_IP, UDP_PORT))

s=""
last=""
sentenceKey = "sentence"

while True:
    data, addr = sock.recvfrom(10000)
    data = json.loads(data)
    print "JSON: ", data
    print "Sentence: ", data[sentenceKey]