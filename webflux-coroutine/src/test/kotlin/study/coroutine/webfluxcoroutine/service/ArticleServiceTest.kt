package study.coroutine.webfluxcoroutine.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.coroutine.webfluxcoroutine.repository.ArticleRepository

@SpringBootTest
class ArticleServiceTest(
    @Autowired private val service: ArticleService,
    @Autowired private val repository: ArticleRepository,
) : StringSpec({

    beforeTest {
        repository.deleteAll()
    }

    "create and get" {
        val created = service.create(ReqCreate("title1"))
        val get = service.get(created.id)
        get.id shouldBe created.id
        get.title shouldBe created.title
        get.body shouldBe created.body
        get.authorId shouldBe created.authorId
        get.createdAt shouldNotBe null
        get.updatedAt shouldNotBe null
    }
    "get all" {
        service.create(ReqCreate("title1"))
        service.create(ReqCreate("title2"))
        service.create(ReqCreate("title matched"))

        service.getAll().toList().size shouldBe 3
        service.getAll("matched").toList().size shouldBe 1
    }
    "update" {
        val created = service.create(ReqCreate("title1"))
        service.update(created.id, ReqUpdate(body = "body updated"))
        val updated = service.get(created.id)

        updated.body shouldBe "body updated"
    }
    "delete" {
        val prevCount = repository.count()
        val created = service.create(ReqCreate("title1"))
        repository.count() shouldBe prevCount + 1
        service.delete(created.id)
        repository.count() shouldBe prevCount
    }
})
