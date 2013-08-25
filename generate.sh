#!/bin/bash

thrift -r --gen java -out src/main/java src/submodules/commons/thrift/service.thrift
thrift -r --gen php:oop,namespace,autoload -out src/main/php/packages src/submodules/commons/thrift/service.thrift
echo -e 'Thrift files successfully generated'

