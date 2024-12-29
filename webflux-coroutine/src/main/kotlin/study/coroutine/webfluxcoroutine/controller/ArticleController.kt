package study.coroutine.webfluxcoroutine.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import study.coroutine.webfluxcoroutine.model.Article
import study.coroutine.webfluxcoroutine.service.ArticleService
import study.coroutine.webfluxcoroutine.service.ReqCreate
import study.coroutine.webfluxcoroutine.service.ReqUpdate

@RestController
@RequestMapping("/article")
class ArticleController(
    private val service: ArticleService,
) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody request: ReqCreate): Article {
        return service.create(request)
    }

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Long): Article {
        return service.get(id)
    }

    @GetMapping("/all")
    suspend fun get(@RequestParam title: String?): Flow<Article> {
        return service.getAll(title)
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody request: ReqUpdate): Article {
        return service.update(id, request)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: Long) {
        return service.delete(id)
    }

}