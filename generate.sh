#!/bin/bash

thrift -r --gen java -out src/main/java src/main/submodules/commons/thrift/service.thrift
thrift -r --gen php:oop,namespace,autoload -out src/main/php/packages src/main/submodules/commons/thrift/service.thrift

