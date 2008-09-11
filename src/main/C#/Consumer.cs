using System;
using Apache.NMS;
using Apache.NMS.Util;
using Apache.NMS.ActiveMQ;

namespace Apache.NMS.ActiveMQ.Book.Ch8
{
   public class Consumer
   {
      public static void Main(string[] args)
      {
	 NMSConnectionFactory NMSFactory = new NMSConnectionFactory("tcp://localhost:61616");
	 IConnection connection = NMSFactory.CreateConnection();
	 ISession session = connection.CreateSession(AcknowledgementMode.AutoAcknowledge);
	 IDestination destination = session.GetTopic("STOCKS.JAVA");
	 IMessageConsumer consumer = session.CreateConsumer(destination);
	 consumer.Listener += new MessageListener(OnMessage);
	 connection.Start();
         Console.WriteLine("Press any key to quit.");
         Console.ReadKey();
      }
   
      protected static void OnMessage(IMessage message)
      {
	ITextMessage TextMessage = message as ITextMessage;
	Console.WriteLine(TextMessage.Text);
      }
   }
}
