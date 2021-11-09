#!/bin/(shell)

UUID=$(curl -X POST -H "Content-Type: application/json" -d "{\"width\":\"10\",\"height\":\"10\"}" http://localhost:8080/mandelbrot)

echo "\n"
curl -X GET "http://localhost:8080/mandelbrot/$UUID" > log.txt
echo "\n"
