#!/usr/bin/env python

# NOTE: This file will serve a contact list to a host, it will also 
# allow a host to register itself as a host.

# USE: 1. Send a contact list addition request, you will then get a 
#        status message back, telling you if your name has been added successfully.
#      2. Request all contacts connected, with information, so you can contact them directly.


import sys, httplib, string, os, socket

#Set up the socket for contact requests
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("",8080))
server_socket.listen(1);

contact_list = {'10.10.1' : 'Thomas Nute','10.10.2' : 'Erik Johnson', '10.10.3' : 'Andrew French'}

def send():
  msg = "".join(["%s:%s;" % (key,value) for key, value in contact_list.items()])
  msg = 'Contacts:' + msg + 'DONE';
  total_sent = 0
  while total_sent < len(msg):
    print "Sending contacts...\n";
    print "Sending data: " , msg[total_sent:]

    sent = client_socket.send(msg[total_sent:])
    print "Sent: " , sent

    if sent == 0:
      raise RuntimeError("socket connection broken")
    
    total_sent += sent
 
  client_socket.close()

print "Waiting for a contact list request..."

while 1:
  try:
    (client_socket, address) = server_socket.accept()
    print "Contact request received from ", address
   
    contact_list[address[0]] = 'Test'

    # For simplicity now that we have a request for contacts, let's add them to the list
    
    while 1:
      data = client_socket.recv(16)
      if (data.find('Contact Request') !=-1) :
        send()
        client_socket.close()
        break;
      else:
        print "Message: ", data
        print "\n"
        break;
  except:
    client_socket.close()
    raise
    break;

