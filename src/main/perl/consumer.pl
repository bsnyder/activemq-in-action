  use Net::Stomp;
  use XML::Simple;



  my $stomp = Net::Stomp->new( { hostname => 'localhost', port => '61613' } );
  $stomp->connect( { login => 'system', passcode => 'manager' } );

  $stomp->subscribe(
      {   destination             => '/topic/STOCKS.JAVA',
          'ack'                   => 'client',
          'activemq.prefetchSize' => 1
      }
  );
  $stomp->subscribe(
      {   destination             => '/topic/STOCKS.IONA',
          'ack'                   => 'client',
          'activemq.prefetchSize' => 1
      }
  );


  my $count = 0;

  while ($count++ < 100) {
    my $frame = $stomp->receive_frame;
    my $xml = XMLin($frame->body);
    print $xml->{name} . "\t" . sprintf("%.2f", $xml->{price}) . "\t";
    print sprintf("%.2f", $xml->{offer}) . "\t" . ($xml->{up} eq 'true' ? 'up' : 'down') . "\n";

    $stomp->ack( { frame => $frame } );
  }

  $stomp->disconnect;
