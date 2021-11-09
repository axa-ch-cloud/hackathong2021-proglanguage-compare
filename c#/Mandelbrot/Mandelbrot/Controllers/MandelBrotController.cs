using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System;
using System.IO;

namespace Mandelbrot.Controllers
{
  [ApiController]
    [Route("[controller]")]
    public class MandelBrotController : ControllerBase
    {
        private const string FILE_PATH = "db";

        public MandelBrotController(ILogger<MandelBrotController> logger)
        {
        }

        [HttpPost]
        public Guid Post(RequestInput input)
        {
            var mandelbrot = Draw(input.Width, input.Height);

            string json = JsonConvert.SerializeObject(mandelbrot, Formatting.Indented);
            Guid fileGuid = Guid.NewGuid();
            System.IO.File.WriteAllText($"{FILE_PATH}/{fileGuid}.json", json);

            return fileGuid;
        }

        [HttpGet]
        public string Get(Guid fileGuid)
        {
            using (StreamReader r = new StreamReader($"{FILE_PATH}/{fileGuid}.json"))
            {
                return r.ReadToEnd();
            }
        }


        private Tuple<int, bool> Mandelbrot(Complex c)
        {
            var maxIterations = 80;
            var z = new Complex();
            int n = 0;
            Complex p;
            double d;

            do
            {
                p = new Complex()
                {
                    X = Math.Pow(z.X, 2) - Math.Pow(z.Y, 2),
                    Y = 2 * z.X * z.Y
                };
                z.X = p.X + c.X;
                z.Y = p.Y + c.Y;

                d = Math.Sqrt(Math.Pow(z.X, 2) + Math.Pow(z.Y, 2));
                n++;
            } while (d <= 2 && n < maxIterations);
            
            return new Tuple<int, bool>(n, d <= 2);
        }

        private Set REAL_SET = new Set()
        {
            Start = -2,
            End = 1
        };
        private Set IMAGINARY_SET = new Set()
        {
            Start = -1,
            End = 1
        };

        private bool[,] Draw(double width, double height)
        {
            bool[,] map = new bool[Convert.ToInt32(width), Convert.ToInt32(height)];
            for (int i=0; i < width; i++)
            {
                for(int j =0; j < height; j++)
                {
                    var complex = new Complex()
                    {
                        X = REAL_SET.Start + (((double)i) / width) * (REAL_SET.End - REAL_SET.Start),
                        Y = IMAGINARY_SET.Start + (((double)j) / height) * (IMAGINARY_SET.End - IMAGINARY_SET.Start)
                    };

                    var t = Mandelbrot(complex);

                    map[i, j] = t.Item2;
                }
            }

            return map;
        }
    }

    class Complex
    {
        public Complex()
        {
            X = 0;
            Y = 0;
        }

        public double X { get; set; }
        public double Y { get; set; }
    }

    class Set
    {
        public double Start { get; set; }
        public double End { get; set; }
    }

    public class RequestInput
    {
        public double Width { get; set; }
        public double Height { get; set; }
    }
}
