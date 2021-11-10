#!/bin/bash
pid=$1
while ps -p $pid -o %cpu,%mem; do
   sleep 0.2
done
