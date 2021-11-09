package com.axa.ch.mandelbrot.repository;

import com.axa.ch.mandelbrot.repository.document.MandelbrotDocument;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Repository
public class MandelbrotRepository {

  public Flux<DataBuffer> save(UUID uuid, MandelbrotDocument document) {
    Path path = Paths.get("data", uuid + ".json");

    AsynchronousFileChannel open = null;
    try {
      open = AsynchronousFileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ResolvableType resolvableType = ResolvableType.forType(MandelbrotDocument.class);

    Mono<DataBuffer> dataBuffer = Mono.just(new Jackson2JsonEncoder().encodeValue(document, new DefaultDataBufferFactory(), resolvableType, null, null));

    return DataBufferUtils.write(dataBuffer, open);
  }

  public Flux<DataBuffer> findByUuid(UUID uuid) {
    Path path = Paths.get("data", uuid + ".json");
    return DataBufferUtils.readAsynchronousFileChannel(() -> AsynchronousFileChannel.open(path, StandardOpenOption.READ), new DefaultDataBufferFactory(), 4096);
  }

}
