<?

require_once('Stomp.php');

$stomp = new Stomp("tcp://localhost:61613");

$stomp->connect('system', 'manager');

$stomp->subscribe("/topic/STOCKS.JAVA");
$stomp->subscribe("/topic/STOCKS.IONA");

$i = 0;
while($i++ < 100) {

    $frame = $stomp->readFrame();
    $xml = new SimpleXMLElement($frame->body);
    echo $xml->attributes()->name . "\t" . number_format($xml->price,2)
       . "\t" . number_format($xml->offer,2)
       . "\t" . ($xml->up == "true"?"up":"down") . "\n";
    $stomp->ack($frame);

}

$stomp->disconnect();


?>
