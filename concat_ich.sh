#!/bin/bash

> src/main/web/templates.ich
for file in src/main/web/templates/*
do
 cat "$file" >> src/main/web/templates.ich
done