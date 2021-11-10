package com.axa.ch.mandelbrot.service;

import com.axa.ch.mandelbrot.api.MandelbrotCreate;
import com.axa.ch.mandelbrot.repository.MandelbrotRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MandelbrotService {

    private static final double REAL_SET_START = -2;
    private static final double REAL_SET_END = 1;

    private static final double IMAGINARY_SET_START = -1;
    private static final double IMAGINARY_SET_END = 1;

    private static final int MAX_ITERATIONS = 80;

    private final MandelbrotRepository repository;

    public Mono<UUID> createMandelbrot(MandelbrotCreate request) {
        return Mono.fromCallable(() -> draw(request))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(booleans -> {
                UUID uuid = UUID.randomUUID();
                return repository.insert(uuid, booleans)
                    .thenReturn(uuid);
            });
    }

    public Flux<DataBuffer> getMandelbrot(UUID uuid) {
        return repository.findByUuid(uuid);
    }

    public Mono<byte[]> createMandelbrotImage() {
        return Mono.fromCallable(() -> {
            MandelbrotCreate create = new MandelbrotCreate();
            create.setWidth(4096);
            create.setHeight(4096);
            boolean[][] draw = draw(create);

            BufferedImage image = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < draw.length; i++) {
                for (int j = 0; j < draw[i].length; j++) {
                    image.setRGB(i, j, draw[i][j] ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private boolean[][] draw(MandelbrotCreate request) {
        boolean[][] data = new boolean[request.getWidth()][request.getHeight()];
        for (int i = 0; i < request.getWidth(); i++) {
            for (int j = 0; j < request.getHeight(); j++) {
                Coordinates complex = new Coordinates(
                    REAL_SET_START + ((double) i / request.getWidth()) * (REAL_SET_END - REAL_SET_START),
                    IMAGINARY_SET_START + ((double) j / request.getHeight()) * (IMAGINARY_SET_END - IMAGINARY_SET_START)
                );

                Map.Entry<Integer, Boolean> result = mandelbrot(complex);
                data[i][j] = result.getValue();
            }
        }
        return data;
    }

    private Map.Entry<Integer, Boolean> mandelbrot(Coordinates c) {
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
        } while (d <= 2 && n < MAX_ITERATIONS);

        return new AbstractMap.SimpleImmutableEntry<>(n, d <= 2);
    }

    @Data
    @AllArgsConstructor
    public static class Coordinates {
        private double x;
        private double y;
    }

}
