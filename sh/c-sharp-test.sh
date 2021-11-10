#!/bin/(shell)

UUID=$(curl --silent  -X POST -H "Content-Type: application/json" -d "{\"width\":10000,\"height\":10000}" http://localhost:5000/mandelbrot)

echo "\n"
curl --silent  -X GET "http://localhost:5000/mandelbrot/?fileUuid=$UUID" > log.txt
echo "\n"
