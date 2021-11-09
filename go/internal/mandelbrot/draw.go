package mandelbrot

var realSet = &complx{x: -2, y: 1}
var imaginarySet = &complx{x: -1, y: 1}

func Draw(width, height int64) (res [][]bool) {
	res = make([][]bool, width)
	for i := range res {
		res[i] = make([]bool, height)
	}

	var i int64
	var j int64

	for i = 0; i < width; i++ {
		for j = 0; j < height; j++ {
			c := &complx{
				x: realSet.x + (float64(i)/float64(width))*(realSet.y-realSet.x),
				y: imaginarySet.x + (float64(j)/float64(height))*(imaginarySet.y-imaginarySet.x),
			}

			_, isSet := calculate(c)
			res[i][j] = isSet
		}
	}
	return
}
