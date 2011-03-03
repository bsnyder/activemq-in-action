#!/usr/bin/env python

import time, sys
from xml.etree.ElementTree import ElementTree, XML
from book import printXml

import stomp

class MyListener(object):
    def on_error(self, headers, message):
        print 'received an error %s' % message

    def on_message(self, headers, message):
        printXml(message)

conn = stomp.Connection()
conn.add_listener(MyListener())
conn.start()
conn.connect()

conn.subscribe(destination='/topic/STOCKS.JAVA', ack='auto')
conn.subscribe(destination='/topic/STOCKS.IONA', ack='auto')




time.sleep(60);

conn.disconnect()
