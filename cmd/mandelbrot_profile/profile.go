package main

import (
	"hackathon/mandelbrot/internal/mandelbrot"
	"log"
	"os"
	"runtime/pprof"
)

func main() {
	f, err := os.Create("profile.prof")
	if err != nil {
		log.Fatal(err)
	}
	pprof.StartCPUProfile(f)
	defer pprof.StopCPUProfile()

	mandelbrot.Draw(1024, 1024)
	mandelbrot.Draw(1024, 1024)
	mandelbrot.Draw(1024, 1024)
}
