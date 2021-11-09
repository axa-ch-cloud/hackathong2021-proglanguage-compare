package main

import (
	"fmt"
	"github.com/gorilla/mux"
	"hackathon/mandelbrot/internal/rest"
	"log"
	"net/http"
)

// Define http methods
const (
	httpGet  = "GET"
	httpPost = "POST"
)

func main() {
	router := mux.NewRouter()
	router.HandleFunc("/mandelbrot", rest.PostMandelbrot).Methods(httpPost)
	router.HandleFunc("/mandelbrot/{uuid}", rest.GetMandelbrot).Methods(httpGet)
	portStr := fmt.Sprintf(":%s", "8080")
	log.Println("will listen on ", portStr)

	if err := http.ListenAndServe(portStr, router); err != nil {
		log.Fatalf("unable to start http server, %s", err)
	}
}
