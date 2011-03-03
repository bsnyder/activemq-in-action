import random
from xml.etree.ElementTree import Element, SubElement, XML, tostring

def mutatePrice(price):
    MAX_DELTA_PERCENT = 1
    percentChange = (2 * random.random() * MAX_DELTA_PERCENT) - MAX_DELTA_PERCENT
    
    return price * (100 + percentChange) / 100;

def createXml(oldPrice, price):
    stock = Element("stock")
    stock.set("name", "JAVA")
    priceElem = SubElement(stock, "price")
    priceElem.text = str(price)

    offer = SubElement(stock, "offer")
    offer.text = str(price * 1.001)

    up = SubElement(stock, "up")
    up.text = str(oldPrice > price)

    return stock

def printXml(text):
    xml = XML(text)

    print "%s\t%.2f\t%.2f\t%s" % (
        xml.get("name"), 
        eval(xml.find("price").text), 
        eval(xml.find("offer").text), 
        "up" if xml.find("up").text == "True" else "down"
        )
