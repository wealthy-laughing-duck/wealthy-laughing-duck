#!/bin/bash

thrift -r --gen java -out src service.thrift
thrift -r --gen php:oop,namespace -out php/packages service.thrift

