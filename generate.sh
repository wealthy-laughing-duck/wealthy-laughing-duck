#!/bin/bash

thrift -r --gen java -out src/main/java service.thrift
thrift -r --gen php:oop,namespace,autoload -out src/main/php/packages service.thrift

