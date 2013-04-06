#!/bin/bash

path="src/main/web"
> "$path"/templates.ich
for file in "$path"/templates/*
do
 cat "$file" >> "$path"/templates.ich
done