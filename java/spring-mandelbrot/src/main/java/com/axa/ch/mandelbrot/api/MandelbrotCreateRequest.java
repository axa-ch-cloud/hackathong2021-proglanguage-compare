package com.axa.ch.mandelbrot.api;

import lombok.Data;

@Data
public class MandelbrotCreateRequest {

  private int width;
  private int heigth;
  private int maxIterations;

}
