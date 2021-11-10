# hackathong2021-proglanguage-compare
 
## Case
Implement calculation of fractals (de: Mandelbrot Menge) in different programming languages and measure different aspects to compare the languages regarding efficienty, sustainability and usability.

## Result

|                         | C#             | Go        | Java JDK 11 | Node    | Rust               | Python* |
| ----------------------- | -------------- | --------- | ----------- | ------- | ------------------ | ------- |
| Tooling / SDK Size      | 334M           | 13M       | 291M        | 102M    | 15M                | ?       |
| Size executable         | 181K           | 6.3M      | 21M         | 256B    | 15M                | ?       |
| Build time              | ~4s            | ~1s       | ~18s        | ~4s     | 3min 35s           | ?       |
| Run time 1k*1k matrix   | 1.33s          | 0.22s     | 0.75s       | 0.45s   | 0.05s              | ?       |
| Run time 10k*10k matrix | 61s            | 25.5s     | 27s         | 45s     | 4.5s               | ?       |
| Max CPU usage           | 100%           | 100%      | 335%        | 100%    | 160%               | ?       |
| Max memory              | 1.3%           | 42.2%     | 35.5%       | 35.7%   | 12%                | ?       |
| Startup time            | ~1s            | 0.000054s | 2.3s        | 0.12s   | Instant like go :) | ?       |
| Loadtest                |                |           |             |         |                    |         |
| Latency                 | scaling issues | 89ms      | 334ms       | 425ms   | 35ms               | ?       |
| Throughput              | scaling issues | 160req/s  | 45req/s     | 20req/s | 240req/s           | ?       |
| Errorrate               | scaling issues | 0%        | 0%          | 0%      | 0%                 | ?       |

(*) we had not enough time to evaluate and setup python 

All the tests were run in an ubuntu environment in docker with the same spec. Those numbers can only be compared to each other, not to another system.

## Conclusion
The overall winner is Go, according to the footprint, buildtime, start time and performance, but it's not widely used within our context. Node performs surprisingly very well and is used broadly within our company. Java is in many aspects the loser, but is surprisingly fast in the bigger calculations, but with a big footprint.
