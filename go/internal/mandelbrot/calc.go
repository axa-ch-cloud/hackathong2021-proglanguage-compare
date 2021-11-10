package mandelbrot

import (
	"math"
)

const maxIteration = 80

type complx struct {
	x float64
	y float64
}

func calculate(c complx) (int, bool) {
	var n int = 0
	var d float64 = 0
	z := complx{x: 0, y: 0}
	p := complx{x: 0, y: 0}

	for d <= 2 && n < maxIteration {
		p.step(z)
		z.add(p, c)
		d = math.Sqrt(z.x*z.x + z.y*z.y)
		n++
	}

	return n, d <= 2
}

func (current *complx) step(z complx) {
	current.x = z.x*z.x - z.y*z.y
	current.y = 2 * z.x * z.y
}

func (current *complx) add(p, c complx) {
	current.x = p.x + c.x
	current.y = p.y + c.y
}
