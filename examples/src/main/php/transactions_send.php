<?
// include a library
require_once("Stomp.php");
// make a connection
$con = new Stomp("tcp://localhost:61613");
// connect
$con->connect();

// try to send some messages
$con->begin("tx1");
for ($i = 1; $i < 3; $i++) {
    $con->send("/queue/transactions", $i, array("transaction" => "tx1"));
}
// if we abort transaction, messages will not be sent
$con->abort("tx1");

// now send some messages for real
$con->begin("tx2");
echo "Sent messages {\n";
for ($i = 1; $i < 5; $i++) {
    $con->send("/queue/transactions", $i, array("transaction" => "tx2"));
    echo "\t$i\n";
}
echo "}\n";
// they will be available for consumers after commit
$con->commit("tx2");

?>