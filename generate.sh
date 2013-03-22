#!/bin/bash

thrift -r --gen java -out src/main/java service.thrift
thrift -r --gen php:oop,namespace -out src/main/php/packages service.thrift

