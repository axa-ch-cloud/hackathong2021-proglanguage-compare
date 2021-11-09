package com.axa.ch.mandelbrot.service;

import com.axa.ch.mandelbrot.api.MandelbrotCreateRequest;
import com.axa.ch.mandelbrot.repository.MandelbrotRepository;
import com.axa.ch.mandelbrot.repository.document.MandelbrotDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MandelbrotService {

  private static final int REAL_SET_START = -2;
  private static final int REAL_SET_END = 1;

  private static final int IMAGINARY_SET_START = -1;
  private static final int IMAGINARY_SET_END = 1;

  private final MandelbrotRepository repository;

  public Mono<UUID> createMandelbrot(MandelbrotCreateRequest request) {
    boolean[][] data = draw(request);

    UUID uuid = UUID.randomUUID();

    MandelbrotDocument document = new MandelbrotDocument();
    document.setData(data);

    return repository.save(uuid, document).
      then(Mono.just(uuid));
  }

  public Flux<DataBuffer> readMandelbrot(UUID uuid) {
    return repository.findByUuid(uuid);
  }

  private boolean[][] draw(MandelbrotCreateRequest request) {
    boolean[][] data = new boolean[request.getWidth()][request.getHeigth()];
    for (int i = 0; i < request.getWidth(); i++) {
      for (int j = 0; j < request.getHeigth(); j++) {
        Coordinates complex = new Coordinates(
          REAL_SET_START + ((double) i / request.getWidth()) * (REAL_SET_END - REAL_SET_START),
          IMAGINARY_SET_START + ((double) j / request.getHeigth()) * (IMAGINARY_SET_END - IMAGINARY_SET_START)
        );

        Map.Entry<Integer, Boolean> result = mandelbrot(complex, request.getMaxIterations());
        data[i][j] = result.getValue();
      }
    }
    return data;
  }

  private Map.Entry<Integer, Boolean> mandelbrot(Coordinates c, int maxIterations) {

    int n = 0;
    Coordinates z = new Coordinates(0, 0);
    Coordinates p;
    double d;

    do {
      p = new Coordinates(
        Math.pow(z.x, 2) - Math.pow(z.y, 2),
        2 * z.x * z.y
      );

      z = new Coordinates(
        p.x + c.x,
        p.y + c.y
      );

      d = Math.sqrt(Math.pow(z.x, 2) + Math.pow(z.y, 2));
      n++;
    } while (d <= 2 && n < maxIterations);

    return new AbstractMap.SimpleImmutableEntry<>(n, d <= 2);
  }

  @Data
  @AllArgsConstructor
  public static class Coordinates {
    private double x;
    private double y;
  }

}
