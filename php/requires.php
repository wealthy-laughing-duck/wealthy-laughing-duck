<?php

$GLOBALS['THRIFT_ROOT'] = __DIR__.'/thrift';

require_once $GLOBALS['THRIFT_ROOT'].'/Thrift.php';
require_once $GLOBALS['THRIFT_ROOT'].'/protocol/TBinaryProtocol.php';
require_once $GLOBALS['THRIFT_ROOT'].'/transport/TSocket.php';
require_once $GLOBALS['THRIFT_ROOT'].'/transport/TBufferedTransport.php';

require_once $GLOBALS['THRIFT_ROOT'].'/packages/service/MainService.php';
require_once $GLOBALS['THRIFT_ROOT'].'/packages/service/service_types.php';
