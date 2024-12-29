package study.coroutine.webfluxcoroutine

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.coroutine.webfluxcoroutine.model.Article
import study.coroutine.webfluxcoroutine.repository.ArticleRepository

@SpringBootTest
class WebfluxCoroutineApplicationTests(
    @Autowired private var repository: ArticleRepository,
) : StringSpec({
    "context load" {
        val pre = repository.count()
        repository.save(Article(title = "title1"))
        val curr = repository.count()

        curr shouldBe pre + 1
    }
})
