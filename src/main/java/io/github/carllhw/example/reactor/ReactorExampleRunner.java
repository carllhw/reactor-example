package io.github.carllhw.example.reactor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.github.carllhw.example.reactor.publisher.FluxExample;

/**
 * reactor example runner
 *
 * @author carllhw
 */
@Component
@Profile("!test")
public class ReactorExampleRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        FluxExample.combineLatestMethod();
    }
}
