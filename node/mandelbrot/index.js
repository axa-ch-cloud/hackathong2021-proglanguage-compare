const MAX_ITERATION = 80
function mandelbrot(c) {
    let z = { x: 0, y: 0 }, n = 0, p, d;
    do {
        p = {
            x: Math.pow(z.x, 2) - Math.pow(z.y, 2),
            y: 2 * z.x * z.y
        }
        z = {
            x: p.x + c.x,
            y: p.y + c.y
        }
        d = Math.sqrt(Math.pow(z.x, 2) + Math.pow(z.y, 2))
        n += 1
    } while (d <= 2 && n < MAX_ITERATION)
    return [n, d <= 2]
}

const REAL_SET = { start: -2, end: 1 }
const IMAGINARY_SET = { start: -1, end: 1 }

function draw(width, height) {

  const result = [];

    for (let i = 0; i < width; i++) {

        const column = [];
        for (let j = 0; j < height; j++) {
            complex = {
                x: REAL_SET.start + (i / width) * (REAL_SET.end - REAL_SET.start),
                y: IMAGINARY_SET.start + (j / height) * (IMAGINARY_SET.end - IMAGINARY_SET.start)
            }

            const [isMandelbrotSet] = mandelbrot(complex);
            column.push(!!isMandelbrotSet)
        }

        result.push(column);
    }

    return result;
}

module.exports.draw = draw;
