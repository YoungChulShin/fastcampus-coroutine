package studt.coroutine

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

private val logger = KotlinLogging.logger {  }
private val scheduler = Schedulers.newSingle("Single")

fun main() {

    Flux.range(1, 10)
        .doOnNext { logger.debug { "1st: $it" } }
        .filter { it % 2 == 0 }
        .doOnNext { logger.debug { "2nd: $it" } }
        .filter { it % 3 == 0 }
        .delayElements(Duration.ofMillis(10), scheduler)
        .doOnNext { logger.debug { "3rd: $it" } }
        .filter { it % 4 == 0 }
        .doOnNext { logger.debug { "4th: $it" } }
        .subscribeOn(scheduler)
        .blockLast()

}