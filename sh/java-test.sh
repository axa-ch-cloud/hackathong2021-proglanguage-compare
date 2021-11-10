#!/usr/bin/env bash
UUID=$(curl --silent  -X POST -H "Content-Type: application/json" -d "{\"width\":10000,\"height\":10000}" http://localhost:8080/mandelbrot | tr -d '"')
echo "\n"
curl --silent  -X GET "http://localhost:8080/mandelbrot/$UUID" > log.txt
echo "\n"
