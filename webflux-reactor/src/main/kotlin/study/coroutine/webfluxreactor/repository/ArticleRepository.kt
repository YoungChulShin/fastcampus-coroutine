package study.coroutine.webfluxreactor.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import study.coroutine.webfluxreactor.model.Article

@Repository
interface ArticleRepository: R2dbcRepository<Article, Long> {

}