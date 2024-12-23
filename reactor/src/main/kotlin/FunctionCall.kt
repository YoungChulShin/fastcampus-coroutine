package studt.coroutine

import mu.KotlinLogging
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger { }

fun getRequest(): Mono<Int> {
    return Mono.just(1)
}

fun subA(request: Int): Mono<Int> {
    return Mono.fromCallable { request + 1 }
}

fun subB(mono: Int): Mono<Int> {
    return Mono.fromCallable { mono + 1 }
}

fun main() {
//    val request = getRequest().doOnNext { logger.debug { ">> request: $it" } }
//    val resA = subA(request).doOnNext { logger.debug { ">> resA: $it" } }
//    val resB = subB(resA).doOnNext { logger.debug { ">> resB: $it" } }
//    resB.subscribe()

    getRequest()
        .doOnNext { logger.debug { ">> request: $it" } }
        .flatMap { subA(it) }
        .doOnNext { logger.debug { ">> subA: $it" } }
        .flatMap { subB(it) }
        .doOnNext { logger.debug { ">> subB: $it" } }
        .subscribe()
}