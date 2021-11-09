package mandelbrot

import (
	"hackathon/mandelbrot/internal/profile"
	"time"
)

var realSet = &complx{x: -2, y: 1}
var imaginarySet = &complx{x: -1, y: 1}

type Matrix struct {
	data       []bool
	rows, cols int64
}

func NewMatrix(rows, cols int64) *Matrix {
	return &Matrix{
		data: make([]bool, cols*rows, cols*rows),
		rows: rows,
		cols: cols,
	}
}

func (m *Matrix) index(x, y int64) int64 {
	return x + m.cols*y
}

func (m *Matrix) coord(i int64) (x, y int64) {
	x = i % m.cols
	y = i / m.cols
	return
}

func Draw(width, height int64) *Matrix {
	defer profile.TimeTrack(time.Now(), "Draw")

	matrix := NewMatrix(width, height)

	var i int64
	var j int64

	for i = 0; i < width; i++ {
		for j = 0; j < height; j++ {
			c := complx{
				x: realSet.x + (float64(i)/float64(width))*(realSet.y-realSet.x),
				y: imaginarySet.x + (float64(j)/float64(height))*(imaginarySet.y-imaginarySet.x),
			}

			_, isSet := calculate(c)

			matrix.data[matrix.index(j, i)] = isSet
		}
	}
	return matrix
}
