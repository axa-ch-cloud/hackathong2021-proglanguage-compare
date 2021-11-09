#!/usr/bin/env bash

UUID=$(curl http://172.20.192.1:8080/mandelbrot -X POST -d '{ "width": 1024, "heigth": 1024 }' --header 'Content-Type: application/json' | tr -d '"')
curl http://172.20.192.1:8080/mandelbrot/$UUID
