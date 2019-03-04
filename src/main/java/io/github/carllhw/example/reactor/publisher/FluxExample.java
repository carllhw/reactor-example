package io.github.carllhw.example.reactor.publisher;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * flux example
 *
 * @author carllhw
 */
@Slf4j
public class FluxExample {

    private FluxExample() {
    }

    /**
     * simple method
     */
    public static void simpleMethod() {
        log.info("Flux.just");
        Flux.just("Hello", "World").subscribe(log::info);
        log.info("Flux.fromArray");
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(data -> log.info(data.toString()));
        log.info("Flux.empty");
        Flux.empty().subscribe(data -> log.info(data.toString()));
        log.info("Flux.range");
        Flux.range(1, 10).subscribe(data -> log.info(data.toString()));
        log.info("Flux.interval");
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(data -> log.info(data.toString()));
    }

    /**
     * generate method
     */
    public static void generateMethod() {
        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(data -> log.info(data.toString()));

        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(data -> log.info(data.toString()));
    }

    /**
     * create method
     */
    public static void createMethod() {
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(data -> log.info(data.toString()));
    }

    /**
     * buffer method
     */
    public static void bufferMethod() {
        Flux.range(1, 100).buffer(20).subscribe(data -> log.info(data.toString()));
        Flux.interval(Duration.ofMillis(100)).buffer(Duration.ofMillis(1001)).take(2).toStream()
                .forEach(data -> log.info(data.toString()));
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(data -> log.info(data.toString()));
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(data -> log.info(data.toString()));
    }

    /**
     * filter method
     */
    public static void filterMethod() {
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(data -> log.info(data.toString()));
    }

    /**
     * window method
     */
    public static void windowMethod() {
        Flux.range(1, 100).window(20).subscribe(data -> log.info(data.toString()));
        Flux.interval(Duration.ofMillis(100)).window(Duration.ofMillis(1001)).take(2).toStream()
                .forEach(data -> log.info(data.toString()));
    }

    /**
     * zipWith method
     */
    public static void zipWithMethod() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(data -> log.info(data.toString()));
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(log::info);
    }

    /**
     * take method
     */
    public static void takeMethod() {
        Flux.range(1, 1000).take(10).subscribe(data -> log.info(data.toString()));
        Flux.range(1, 1000).takeLast(10).subscribe(data -> log.info(data.toString()));
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(data -> log.info(data.toString()));
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(data -> log.info(data.toString()));
    }

    /**
     * reduce method
     */
    public static void reduceMethod() {
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(data -> log.info(data.toString()));
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y)
                .subscribe(data -> log.info(data.toString()));
    }

    /**
     * merge method
     */
    public static void mergeMethod() {
        Flux.merge(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(data -> log.info(data.toString()));
        Flux.mergeSequential(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(data -> log.info(data.toString()));
    }

    /**
     * flat method
     */
    public static void flatMethod() {
        Flux.just(5, 10)
                .flatMap(x -> Flux.interval(Duration.ofMillis(x * 10L), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(data -> log.info(data.toString()));
        Flux.just(5, 10)
                .flatMapSequential(x -> Flux.interval(Duration.ofMillis(x * 10L), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(data -> log.info(data.toString()));
    }

    /**
     * concatMap method
     */
    public static void concatMapMethod() {
        Flux.just(5, 10)
                .concatMap(x -> Flux.interval(Duration.ofMillis(x * 10L), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(data -> log.info(data.toString()));
    }

    /**
     * combineLatest method
     */
    public static void combineLatestMethod() {
        Flux.combineLatest(Arrays::toString,
                Flux.interval(Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(log::info);
    }
}
