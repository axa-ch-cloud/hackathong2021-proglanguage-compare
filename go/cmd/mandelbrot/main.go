package main

import (
	"fmt"
	"hackathon/mandelbrot/internal/rest"
	"log"
	"net/http"
	"time"

	"github.com/gorilla/mux"
)

// Define http methods
const (
	httpGet  = "GET"
	httpPost = "POST"
)

var start = time.Now()

func main() {
	router := mux.NewRouter()
	router.HandleFunc("/mandelbrot", rest.PostMandelbrot).Methods(httpPost)
	router.HandleFunc("/mandelbrot/{uuid}", rest.GetMandelbrot).Methods(httpGet)
	fmt.Printf("Startuptime: %v\n", time.Since(start))
	if err := http.ListenAndServe(":8080", router); err != nil {
		log.Fatalf("unable to start http server, %s", err)
	}
}
