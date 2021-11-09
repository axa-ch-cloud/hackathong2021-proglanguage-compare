package mandelbrot

var realSet = complx{x: -2, y: 1}
var imaginarySet = complx{x: -1, y: 1}

func Draw(width, height int64) [][]bool {
	matrix := make([][]bool, height)
	for i := range matrix {
		matrix[i] = make([]bool, width)
	}

	var i int64
	var j int64

	for i = 0; i < width; i++ {
		for j = 0; j < height; j++ {
			c := complx{
				x: realSet.x + (float64(i)/float64(width))*(realSet.y-realSet.x),
				y: imaginarySet.x + (float64(j)/float64(height))*(imaginarySet.y-imaginarySet.x),
			}

			_, isSet := calculate(c)

			matrix[i][j] = isSet
		}
	}
	return matrix
}
