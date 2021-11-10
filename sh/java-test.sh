#!/usr/bin/env bash
UUID=$(curl -X POST -H "Content-Type: application/json" -d "{\"width\":1000,\"height\":1000}" http://localhost:8080/mandelbrot | tr -d '"')
echo "\n"
curl -X GET "http://localhost:8080/mandelbrot/$UUID" > log.txt
echo "\n"
