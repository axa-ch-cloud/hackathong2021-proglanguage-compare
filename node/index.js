#!/usr/bin/env node

const express = require('express');
const fs = require('fs');
const { draw } = require('./mandelbrot');
const app = express();
const port = 8080;

app.use(express.json());

function createUUID() {
  let s = [];
  let hexDigits = "0123456789abcdef";
  for (let i = 0; i < 36; i++) {
      s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = "-";

  let uuid = s.join("");
  return uuid;
};

app.post('/mandelbrot', (req, res) => {
  const { width, height } = req.body;
  const result = draw(width, height);
  const uuid = createUUID();
  fs.writeFile(`./db/${uuid}.json`, JSON.stringify(result), () => {
    res.send(uuid);
  });
});

app.get('/mandelbrot/:uuid', (req, res) => {
  const { uuid } = req.params;
  fs.readFile(`./db/${uuid}.json`, 'utf-8', (err, data) => {
    const result = JSON.parse(data);
    res.send(result);
  });
});
/*

FOR LOCAL DEV

app.get('/local', (req, res) => {
  res.send(`
    <script>
    (async () => {
      const rawResponse = await fetch('http://localhost:8080/mandelbrot', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ width: 10, height: 10 })
      });
      const content = await rawResponse.json();

      console.log(content);
    })();
    </script>
  `)
});

app.get('/', (req, res) => {
  res.send(`use <a href="http://localhost:${port}/local">/local</a> to test`)
});

*/

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
});
