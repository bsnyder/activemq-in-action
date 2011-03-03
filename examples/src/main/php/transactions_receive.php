<?php

// include a library
require_once("Stomp.php");
// make a connection
$con = new Stomp("tcp://localhost:61613");
// connect
$con->connect();
$con->setReadTimeout(1);

// subscribe to the queue
$con->subscribe("/queue/transactions", array('ack' => 'client','activemq.prefetchSize' => 1 ));

// try to receive some messages
$con->begin("tx3");
$messages = array();
for ($i = 1; $i < 3; $i++) {
    $msg = $con->readFrame();
    array_push($messages, $msg);
    $con->ack($msg, "tx3");
}
// of we abort transaction, we will "rollback" out acks
$con->abort("tx3");

$con->begin("tx4");
// so we need to ack received messages again
// before we can receive more (prefetch = 1)
if (count($messages) != 0) {
    foreach($messages as $msg) {
        $con->ack($msg, "tx4");
    }
}
// now receive more messages
for ($i = 1; $i < 3; $i++) {
    $msg = $con->readFrame();
    $con->ack($msg, "tx4");
    array_push($messages, $msg);
}
// commit all acks
$con->commit("tx4");


echo "Processed messages {\n";
foreach($messages as $msg) {
    echo "\t$msg->body\n";
}
echo "}\n";

//ensure there are no more messages in the queue
$frame = $con->readFrame();

if ($frame === false) {
    echo "No more messages in the queue\n";
} else {
    echo "Warning: some messages still in the queue: $frame\n";
}

// disconnect
$con->disconnect();