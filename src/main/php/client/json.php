<?php

require_once dirname(__FILE__) . '/../requires.php';

use \SymfonyWorld\Thrift;
use \SymfonyWorld\WealthyLaughingDuck\TCategoryType;

$request = count($_POST) ? $_POST : $_GET;
$client = new Thrift("localhost", 9090);

if ($request['action'] == 'outcomes')
{
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
}
elseif ($request['action'] == 'users')
{
  $result = $client->getClient()->getAllUsers();
}
elseif ($request['action'] == 'incomeCategories')
{
  $result = $client->getClient()->getCategoryTree(
    TCategoryType::INCOME
  );
}
elseif ($request['action'] == 'outcomeCategories')
{
  $result = $client->getClient()->getCategoryTree(
    TCategoryType::OUTCOME
  );
}
elseif ($request['action'] == 'createNode')
{
  $type_property = strtoupper($request['type']);
  $type = constant("\SymfonyWorld\WealthyLaughingDuck\TCategoryType::$type_property");
  $result = $client->getClient()->createCategoryTreeNode(
    $type,
    $request['name'],
    $request['parent_id']
  );
}
elseif ($request['action'] == 'renameNode')
{
  $result = $client->getClient()->renameCategoryTreeNode(
    $request['id'],
    $request['new_name']
  );
}

echo json_encode($result);
