package rest

import (
	"encoding/json"
	"hackathon/mandelbrot/internal/db"
	"hackathon/mandelbrot/internal/mandelbrot"
	"io/ioutil"
	"net/http"

	"github.com/gorilla/mux"
)

func GetMandelbrot(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	uuid := vars["uuid"]
	res, err := db.Get(uuid)

	if err != nil {
		panic(err)
	}

	if _, err = w.Write(res); err != nil {
		panic(err)
	}
}

func PostMandelbrot(w http.ResponseWriter, r *http.Request) {
	reqBody, _ := ioutil.ReadAll(r.Body)
	var params Params
	err := json.Unmarshal(reqBody, &params)
	if err != nil {
		panic(err)
	}

	mandelbrotRes := mandelbrot.Draw(params.Width, params.Height)
	uuid, err := db.Store(mandelbrotRes)

	if err != nil {
		panic(err)
	}

	if _, err = w.Write([]byte(uuid)); err != nil {
		panic(err)
	}
}

type Params struct {
	Width  int64 `json:"width"`
	Height int64 `json:"height"`
}
