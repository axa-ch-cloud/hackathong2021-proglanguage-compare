package db

import "hackathon/mandelbrot/internal/mandelbrot"

type Entity struct {
	Result mandelbrot.Matrix `json:"result"`
}

func New(matrix *mandelbrot.Matrix) Entity {
	return Entity{
		Result: *matrix,
	}
}
