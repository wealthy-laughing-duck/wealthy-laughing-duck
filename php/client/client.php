<?php

require_once('../requires.php');
require_once('Thrift.php');

use \SymfonyWorld\Thrift;

$client = new Thrift("localhost", 9091);
$add = $client->getClient()->add(10, 20);
$sub = $client->getClient()->sub(421, 65);
echo "Addition result is $add and subtraction result is $sub\n";
// = $client->executeCommand("add", array('n1' => 10, 'n2' => 20));

