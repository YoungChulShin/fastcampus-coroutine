package study.coroutine.webfluxcoroutine.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import study.coroutine.webfluxcoroutine.model.Article

@Repository
interface ArticleRepository: CoroutineCrudRepository<Article, Long> {
}