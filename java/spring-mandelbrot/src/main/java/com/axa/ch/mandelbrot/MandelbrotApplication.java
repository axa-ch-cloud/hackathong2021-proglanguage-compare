package com.axa.ch.mandelbrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MandelbrotApplication {

    public static void main(String[] args) {
        new File("data").mkdir();

        SpringApplication.run(MandelbrotApplication.class, args);
    }

}
