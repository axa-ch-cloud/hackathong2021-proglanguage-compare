#!/usr/bin/env node

const express = require('express');
const { mandelbrot } = require('./mandelbrot');
const app = express();
const port = 3000;

app.use(express.json());

app.post('/mandelbrot', (req, res) => {

  console.log(req.body.seed);
  res.send( mandelbrot(req.body.seed));
});

app.get('/', (req, res) => {
  res.send(`

    <script>
    (async () => {
      const rawResponse = await fetch('http://localhost:3000/mandelbrot', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({seed: 4000})
      });
      const content = await rawResponse.json();

      console.log(content);
    })();
    </script>
  `)
});

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
});
