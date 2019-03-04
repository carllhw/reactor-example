package io.github.carllhw.example.reactor.publisher;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * mono example
 *
 * @author carllhw
 */
@Slf4j
public class MonoExample {

    private MonoExample() {
    }

    /**
     * create sequence
     */
    public static void createMethod() {
        Mono.fromSupplier(() -> "Mono.fromSupplier").subscribe(log::info);
        Mono.justOrEmpty(Optional.of("Mono.justOrEmpty")).subscribe(log::info);
        Mono.create(sink -> sink.success("Mono.create")).subscribe(data -> log.info(data.toString()));
    }
}
