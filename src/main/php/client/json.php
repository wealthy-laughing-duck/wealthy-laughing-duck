<?php

require_once dirname(__FILE__) . '/../requires.php';

use \SymfonyWorld\Thrift;

$request = $_GET;
$client = new Thrift("localhost", 9090);

$outcomes = $client->getClient()->getUserOutcomes(2);
$outcomes = array_slice($outcomes, $request['iDisplayStart'], $request['iDisplayLength']);

$result = array("aaData" => array());
foreach ($outcomes as $outcome) {
  $result["aaData"][] = array(
    $outcome->amount,
    $outcome->user,
    $outcome->category,
    $outcome->comment
  );
}

echo json_encode($result);
