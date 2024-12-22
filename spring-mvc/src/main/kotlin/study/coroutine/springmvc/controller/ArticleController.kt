package study.coroutine.springmvc.controller

import org.springframework.web.bind.annotation.*
import study.coroutine.springmvc.model.Article
import study.coroutine.springmvc.service.ArticleService
import study.coroutine.springmvc.service.ReqCreate
import study.coroutine.springmvc.service.ReqUpdate

@RestController
@RequestMapping("/article")
class ArticleController(
    private val service: ArticleService,
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Article {
        return service.get(id)
    }

    @GetMapping("/all")
    fun getAll(@RequestParam title: String?): List<Article> {
        return service.getAll(title)
    }

    @PostMapping("")
    fun create(@RequestBody request: ReqCreate): Article {
        return service.create(request)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: ReqUpdate): Article {
        return service.update(id, request)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }


}