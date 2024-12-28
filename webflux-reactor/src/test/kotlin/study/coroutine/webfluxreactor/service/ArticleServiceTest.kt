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
        Assertions.assertThat(readArticle.createdAt).isNotNull()
        Assertions.assertThat(readArticle.updatedAt).isNotNull()
    }

    @Test
    fun getAll() {
        service.create(ReqCreate(title = "title1", body = "body1")).block()!!
        service.create(ReqCreate(title = "title2", body = "body2")).block()!!
        service.create(ReqCreate(title = "title matched", body = "body3")).block()!!

        Assertions.assertThat(service.getAll().collectList().block()!!.size).isEqualTo(3)
        Assertions.assertThat(service.getAll("matched").collectList().block()!!.size).isEqualTo(1)
    }

    @Test
    fun update() {
        val new = service.create(ReqCreate(title = "title1", body = "body1", authorId = 1234)).block()!!
        val request = ReqUpdate(
            title = "updated !",
            body = "update body !"
        )

        service.update(new.id, request).block()

        service.get(new.id).block()!!.let { article ->
            Assertions.assertThat(article.title).isEqualTo(request.title)
            Assertions.assertThat(article.body).isEqualTo(request.body)
            Assertions.assertThat(article.authorId).isEqualTo(new.authorId)
        }
    }

    @Test
    fun delete() {
        val prevCount = repository.count().block() ?: 0
        val article = service.create(ReqCreate(title = "title1", body = "body1")).block()!!

        service.delete(article.id).block()
        val currCount = repository.count().block() ?: 0

        Assertions.assertThat(currCount).isEqualTo(prevCount)
    }
}