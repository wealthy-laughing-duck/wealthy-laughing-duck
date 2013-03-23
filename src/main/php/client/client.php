<?php

require_once dirname(__FILE__) . '/../requires.php';

use \SymfonyWorld\Thrift;

$client = new Thrift("localhost", 9090);
$add = $client->getClient()->add(10, 67);
$sub = $client->getClient()->sub(421, 194);
echo "Addition result is $add and subtraction result is $sub\n";
// = $client->executeCommand("add", array('n1' => 10, 'n2' => 20));

