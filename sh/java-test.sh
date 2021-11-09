#!/usr/bin/env bash

UUID=$(curl http://localhost:8080/mandelbrot -X POST -d '{ "width": 1024, "heigth": 1024 }' --header 'Content-Type: application/json' | tr -d '"')
curl http://localhost:8080/mandelbrot/$UUID
