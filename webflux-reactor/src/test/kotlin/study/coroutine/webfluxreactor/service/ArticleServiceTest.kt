package study.coroutine.webfluxreactor.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.coroutine.webfluxreactor.repository.ArticleRepository

@SpringBootTest
class ArticleServiceTest(
    @Autowired private val service: ArticleService,
    @Autowired private val repository: ArticleRepository,
) {

    @Test
    fun createAndGet() {
        val prevCount = repository.count().block() ?: 0
        val article = service.create(ReqCreate(title = "title1", body = "body1")).block()!!
        val currCount = repository.count().block() ?: 0

        Assertions.assertThat(currCount).isEqualTo(prevCount + 1)

        val readArticle = service.get(article.id).block()!!
        Assertions.assertThat(readArticle.id).isEqualTo(article.id)
        Assertions.assertThat(readArticle.title).isEqualTo(article.title)
        Assertions.assertThat(readArticle.body).isEqualTo(article.body)
        Assertions.assertThat(readArticle.authorId).isEqualTo(article.authorId)
    }
}