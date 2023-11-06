package sample

import mu.KotlinLogging
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {  }

fun main() {
    measureTimeMillis {
        subA().subscribe()
        subB().block()
    }.let { logger.debug { ">> elapsed: $it ms" } }
}

private fun subA(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "start A" } }
        .delayElement(Duration.ofSeconds(1))
        .doOnNext { logger.debug { "end A" } }
}

private fun subB(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "start B" } }
        .delayElement(Duration.ofSeconds(1))
        .doOnNext { logger.debug { "end B" } }
}