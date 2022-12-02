#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import socket

HOST = '192.168.64.1'
PORT = 7000

navi_info = ""

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((HOST, PORT))
s.listen(5)

print('server start at: %s:%s' % (HOST, PORT))
print('wait for connection...')

'''
def socketforapp()  :

    while True:

        conn, addr = s.accept()
        print('connected by ' + str(addr))

        while True:

            #f = open("routePlan.txt", 'w')
            indata = conn.recv(1024)
            if len(indata) == 0: # connection closed
                conn.close()
                print('client closed connection.')
                break
            
            print('recv: ' + indata.decode())
            #f.write( str(indata.decode()) )
            outdata = 'echo ' + indata.decode()
            conn.send(outdata.encode())

            #f.close()
''' 
def socketforapp():
    while True:
        conn, addr = s.accept()
        print( 'connected by', addr )

        while True:
            try:
                indata = conn.recv(1024)
                if len(indata) == 0:
                    continue
                print(indata)
                print( 'recv: ' + indata.decode() )
                #outdata = 'echo ' + indata.decode()
                #conn.send(outdata.encode())
            
            except:
                print( "ERROR, reconnecting" )
                break

socketforapp()