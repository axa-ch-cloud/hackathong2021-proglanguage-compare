#!/bin/(shell)

UUID=$(curl --silent -X POST -H "Content-Type: application/json" -d "{\"width\":1000,\"height\":1000}" http://localhost:8080/mandelbrot)

echo "\n"
curl --silent -X GET "http://localhost:8080/mandelbrot/$UUID" > log.txt
echo "\n"
