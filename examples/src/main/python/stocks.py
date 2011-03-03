import pyactivemq, time, sys, random
from pyactivemq import ActiveMQConnectionFactory
from book import mutatePrice, printXml, createXml
from xml.etree.ElementTree import Element, SubElement, XML, tostring

class MessageListener(pyactivemq.MessageListener):

    def onMessage(self, message):
        printXml(message.text)

nmessages = 100

brokerUrl = 'tcp://localhost:61616?wireFormat=openwire'

if(len(sys.argv) == 2 and sys.argv[1] == 'stomp'):
    brokerUrl = 'tcp://localhost:61613?wireFormat=stomp'

print 'connecting to: ', brokerUrl 

f = ActiveMQConnectionFactory(brokerUrl)
conn = f.createConnection()

session = conn.createSession()
topic = session.createQueue('stocks')
producer = session.createProducer(topic)

consumer = session.createConsumer(topic)
consumer.messageListener = MessageListener()

conn.start()

textMessage = session.createTextMessage()
price = random.uniform(1, 100)

for i in xrange(nmessages):
    oldPrice = price
    price = mutatePrice(price)

    textMessage.text = tostring(createXml(oldPrice, price))
    producer.send(textMessage)

time.sleep(5)

conn.close()
