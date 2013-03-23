<?php

$THRIFT_ROOT = __DIR__;
require_once $THRIFT_ROOT . '/Thrift/ClassLoader/ThriftClassLoader.php';
require_once $THRIFT_ROOT . '/client/Thrift.php';

use \Thrift\ClassLoader\ThriftClassLoader;

$loader = new ThriftClassLoader();
$loader->registerNamespace('Thrift', $THRIFT_ROOT);
$loader->registerNamespace('SymfonyWorld', $THRIFT_ROOT . '/packages');
$loader->register();
$loader->loadClass('SymfonyWorld\WealthyLaughingDuck\FinanceService');
