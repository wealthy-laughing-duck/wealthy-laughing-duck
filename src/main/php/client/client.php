<?php

require_once dirname(__FILE__) . '/../requires.php';

use \SymfonyWorld\Thrift;

$client = new Thrift("localhost", 9090);
$outcomes = $client->getClient()->getUserOutcomes(2);
foreach ($outcomes as $outcome) {
  echo $outcome->amount . ", " . $outcome->user . ", "
    . $outcome->category  . ", " . $outcome->comment . "\n";
}
// = $client->executeCommand("add", array('n1' => 10, 'n2' => 20));
