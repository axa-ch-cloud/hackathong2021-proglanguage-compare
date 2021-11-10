package com.axa.ch.mandelbrot.controller;

import com.axa.ch.mandelbrot.api.MandelbrotCreate;
import com.axa.ch.mandelbrot.service.MandelbrotService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/mandelbrot")
@RequiredArgsConstructor
public class MandelbrotController {

    private final MandelbrotService service;

    @PostMapping(
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    public Mono<UUID> createMandelbrot(@RequestBody MandelbrotCreate request) {
        return service.createMandelbrot(request);
    }

    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    public Flux<DataBuffer> getMandelbrot(@PathVariable UUID uuid) {
        return service.getMandelbrot(uuid);
    }

    @GetMapping(value = "/png", produces = IMAGE_PNG_VALUE)
    public Mono<byte[]> getMandelbrotImage() {
        return service.createMandelbrotImage();
    }

}
