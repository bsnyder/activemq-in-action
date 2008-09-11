#!/usr/bin/ruby

require 'rubygems'
require 'stomp'
require 'xmlsimple'

@conn = Stomp::Connection.open 'system', 'manager', 'localhost', 61613, false 
@count = 0

@conn.subscribe "/topic/STOCKS.JAVA", { :ack =>"auto" }
@conn.subscribe "/topic/STOCKS.IONA", { :ack =>"auto" }
while @count < 100 
	@msg = @conn.receive
	@count = @count + 1
	if @msg.command == "MESSAGE"
		@xml = XmlSimple.xml_in(@msg.body)
		$stdout.print "#{@xml['name']}\t"
		$stdout.print "#{'%.2f' % @xml['price']}\t"
		$stdout.print "#{'%.2f' % @xml['offer']}\t"
		$stdout.print "#{@xml['up'].to_s == 'true'?'up':'down'}\n"
	else
 		$stdout.print "#{@msg.command}: #{@msg.body}\n"
	end
end
@conn.disconnect