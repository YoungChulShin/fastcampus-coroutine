package study.coroutine.webfluxcoroutine.controller

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import study.coroutine.webfluxcoroutine.model.Article
import study.coroutine.webfluxcoroutine.repository.ArticleRepository
import study.coroutine.webfluxcoroutine.service.ArticleService
import study.coroutine.webfluxcoroutine.service.ReqCreate
import study.coroutine.webfluxcoroutine.service.ReqUpdate
import java.time.temporal.ChronoUnit

@SpringBootTest
@ActiveProfiles("test")
class ArticleControllerTest(
    @Autowired private val repository: ArticleRepository,
    @Autowired private val service: ArticleService,
    @Autowired private val context: ApplicationContext,
) : StringSpec({

    val client = WebTestClient.bindToApplicationContext(context).build()

    beforeTest {
        repository.deleteAll()
    }


    fun getSize(title: String? = null): Int {
        return client.get().uri("/article/all${title?.let { "?title=$it" } ?: ""}")
            .accept(MediaType.APPLICATION_JSON).exchange()
            .expectBody(List::class.java)
            .returnResult().responseBody?.size ?: 0
    }

    "create" {
        val request = ReqCreate("title test", "r2dbc coroutine", 1234)
        client.post().uri("/article").accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("title").isEqualTo(request.title)
            .jsonPath("body").isEqualTo(request.body ?: "")
            .jsonPath("authorId").isEqualTo(request.authorId ?: 0)
    }

    "get" {
        val created = client.post().uri("/article").accept(MediaType.APPLICATION_JSON)
            .bodyValue(ReqCreate("title test", "r2dbc coroutine", 1234))
            .exchange()
            .expectBody(Article::class.java)
            .returnResult().responseBody!!
        val read = client.get().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody(Article::class.java)
            .returnResult().responseBody!!

        read.id shouldBe created.id
        read.title shouldBe created.title
        read.body shouldBe created.body
        read.authorId shouldBe created.authorId
        read.createdAt?.truncatedTo(ChronoUnit.SECONDS) shouldBe created.createdAt?.truncatedTo(
            ChronoUnit.SECONDS
        )
        read.updatedAt?.truncatedTo(ChronoUnit.SECONDS) shouldBe created.updatedAt?.truncatedTo(
            ChronoUnit.SECONDS
        )
    }

    "get all" {
        service.create(ReqCreate("title1"))
        service.create(ReqCreate("title2"))
        service.create(ReqCreate("title matched"))

        getSize() shouldBe 3
        getSize("matched") shouldBe 1
    }

    "update" {
        val created = service.create(ReqCreate("title1"))

        client.put().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .bodyValue(ReqUpdate(title = "updated title"))
            .exchange()

        client.get().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody()
            .jsonPath("title").isEqualTo("updated title")

    }

    "delete" {
        val created = service.create(ReqCreate("title1"))
        val precCount = repository.count()

        client.delete().uri("/article/${created.id}").accept(MediaType.APPLICATION_JSON).exchange()

        repository.count() shouldBe precCount - 1
        repository.existsById(created.id) shouldBe false
    }


})


