use actix_web::{get, post, web, App, HttpRequest, HttpServer, Result};
use std::fs;
use serde::Deserialize;
use serde_json;
use uuid::Uuid;
use std::fs::File;
use std::io::Write;

#[derive(Deserialize)]
struct MandelbrotPayload {
    width: usize,
    height: usize,
}

#[get("/mandelbrot/{uuid}")]
async fn hello(req: HttpRequest) -> Result<String> {
    let uuid: String = req.match_info().get("uuid").unwrap().parse().unwrap();
    let content = fs::read_to_string(format!("data/{}.json", uuid))
        .expect("Something went wrong reading the file");

    return Ok(format!("{}", content));
}

#[post("/mandelbrot")]
async fn echo(request: web::Json<MandelbrotPayload>) -> Result<String> {
    let data = mandelbrot(request.height, request.width);
    let json_string = serde_json::to_string(&data).unwrap();

    let uuid = Uuid::new_v4();
    let uuid_string =  uuid.to_hyphenated();

    let mut output = File::create(format!("data/{}.json",  uuid_string))
        .expect("Unable to create file");

    write!(output, "{}", json_string)?;

    return Ok(uuid_string.to_string());
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    println!("Starting...");

    HttpServer::new(|| {
        App::new()
            .service(hello)
            .service(echo)
    })
    .bind("0.0.0.0:8080")?
    .run()
    .await
}

fn mandelbrot(height: usize, width: usize) -> Vec<Vec<bool>> {
    let mut data = vec![vec![false; width]; height];
    for i in 0..width {
        for j in 0..height {
            let complex = Coordinates{
                x: -2.0 + (i as f64 / width as f64) * (1.0 + 2.0),
                y: -1.0 + (j as f64 / height as f64) * (1.0 + 1.0)
            };

            let (_, is_mandelbrot) = calc(complex);
            data[i][j] = is_mandelbrot;
        }
    }

    return data;
}

fn calc(c: Coordinates) -> (i32, bool) {
    let mut n = 0;
    let mut z = Coordinates{x: 0.0, y: 0.0};
    let mut d = 0.0;

    while n < 80 {
        let p = Coordinates{
            x: z.x * z.x - z.y * z.y, 
            y: 2.0 * z.x * z.y
        };

        z = Coordinates{
            x: p.x + c.x,
            y: p.y + c.y
        };

        d = (z.x * z.x + z.y * z.y).sqrt();

        if d <= 2.0 {
            break;
        }

        n+=1;
    }

    return (n, d <= 2.0);
}

struct Coordinates {
    x: f64,
    y: f64
}
