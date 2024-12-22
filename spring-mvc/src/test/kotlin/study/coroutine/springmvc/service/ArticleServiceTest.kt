package study.coroutine.springmvc.service

import org.assertj.core.api.Assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ExtendWith(MockitoExtension::class)
@Rollback
class ArticleServiceTest(
    @Autowired private val service: ArticleService,
) {

    @Test
    fun `create and get`() {
        val article = service.create(
            ReqCreate(
                title = "title",
                body = "body",
                authorId = 1234
            )
        ).let { service.get(it.id) }

        Assertions.assertThat(article.title).isEqualTo("title")
        Assertions.assertThat(article.body).isEqualTo("body")
        Assertions.assertThat(article.authorId).isEqualTo(1234)
    }

    @Test
    fun getAll() {
        repeat(5) { i ->
            service.create(
                ReqCreate(
                    title = "title $i",
                    body = "body $i",
                    authorId = 1234
                ))
        }

        Assertions.assertThat(service.getAll().size).isGreaterThanOrEqualTo(5)
    }

    @Test
    fun update() {
        val create = service.create(
            ReqCreate(
                title = "title",
                body = "body",
                authorId = 1234
            ))

        service.update(
            create.id,
            ReqUpdate(title = "updated"))

        val updated = service.get(create.id)

        Assertions.assertThat(updated.title).isEqualTo("updated")
    }

    @Test
    fun delete() {
        val create = service.create(
            ReqCreate(
                title = "title",
                body = "body",
                authorId = 1234
            ))

        val prev = service.getAll().size
        service.delete(create.id)
        val curr = service.getAll().size

        Assertions.assertThat(curr + 1).isEqualTo(prev)
    }
}