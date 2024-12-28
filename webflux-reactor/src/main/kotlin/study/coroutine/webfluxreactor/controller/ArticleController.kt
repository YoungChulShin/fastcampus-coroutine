package study.coroutine.webfluxreactor.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import study.coroutine.webfluxreactor.model.Article
import study.coroutine.webfluxreactor.service.ArticleService
import study.coroutine.webfluxreactor.service.ReqCreate
import study.coroutine.webfluxreactor.service.ReqUpdate

@RestController
@RequestMapping("/article")
class ArticleController(
    private val service: ArticleService,
) {

    @PostMapping("")
    fun create(@RequestBody request: ReqCreate): Mono<Article> {
        return service.create(request)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Mono<Article> {
        return service.get(id)
    }

    @GetMapping("/all")
    fun getAll(@RequestParam title: String?): Flux<Article> {
        return service.getAll(title)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: ReqUpdate): Mono<Article> {
        return service.update(id, request)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> {
        return service.delete(id)
    }
}