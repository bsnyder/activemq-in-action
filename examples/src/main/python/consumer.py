#!/usr/bin/env python

import time
import sys
from elementtree.ElementTree import ElementTree, XML

import stomp

class MyListener(object):
    def on_error(self, headers, message):
        print 'received an error %s' % message

    def on_message(self, headers, message):
        xml = XML(message)

        print "%s\t%.2f\t%.2f\t%s" % (
            xml.get("name"), 
            eval(xml.find("price").text), 
            eval(xml.find("offer").text), 
            "up" if xml.find("up").text == "true" else "down"
            )

conn = stomp.Connection()
conn.add_listener(MyListener())
conn.start()
conn.connect()

conn.subscribe(destination='/topic/STOCKS.JAVA', ack='auto')
conn.subscribe(destination='/topic/STOCKS.IONA', ack='auto')




time.sleep(60);

conn.disconnect()
