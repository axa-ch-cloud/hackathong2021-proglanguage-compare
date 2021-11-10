package com.axa.ch.mandelbrot.repository;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Repository
public class MandelbrotRepository {

    public Mono<Void> insert(UUID uuid, boolean[][] data) {
        Mono<DataBuffer> dataBuffer = Mono.fromCallable(() -> {
            ResolvableType resolvableType = ResolvableType.forType(boolean[][].class);

            return new Jackson2JsonEncoder().encodeValue(
                data, new DefaultDataBufferFactory(), resolvableType, null, null);
        }).subscribeOn(Schedulers.boundedElastic());

        return DataBufferUtils.write(dataBuffer, Paths.get("data", uuid + ".json"), CREATE, WRITE);
    }

    public Flux<DataBuffer> findByUuid(UUID uuid) {
        Path path = Paths.get("data", uuid + ".json");
        return DataBufferUtils.readAsynchronousFileChannel(() ->
            AsynchronousFileChannel.open(path, StandardOpenOption.READ), new DefaultDataBufferFactory(), 16384);
    }

}
