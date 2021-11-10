from mandelbrot import mandelbrot, MAX_ITER


# Plot window
RE_START = -2
RE_END = 1
IM_START = -1
IM_END = 1

palette = []

def draw(width, height):
  result = []
  for x in range(0, width):
      columns = []
      for y in range(0, height):
          # Convert pixel coordinate to complex number
          c = complex(RE_START + (x / width) * (RE_END - RE_START),
                      IM_START + (y / height) * (IM_END - IM_START))
          # Compute the number of iterations
          m = mandelbrot(c)
          columns.append(bool(m))
      result.append(columns)
  return result

