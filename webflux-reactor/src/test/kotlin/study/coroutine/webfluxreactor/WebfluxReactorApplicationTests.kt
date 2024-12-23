package study.coroutine.webfluxreactor

import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.coroutine.webfluxreactor.model.Article
import study.coroutine.webfluxreactor.repository.ArticleRepository

private val logger = KotlinLogging.logger {  }

@SpringBootTest
class WebfluxReactorApplicationTests(
    @Autowired private val repository: ArticleRepository,
) {

    @Test
    fun contextLoads() {
        val count = repository.count().block() ?: 0
        repository.save(Article(title = "title1")).block()
        val articles = repository.findAll().collectList().block()
        articles?.forEach { logger.debug { it } }

        val currCount = repository.count().block() ?: 0
        Assertions.assertThat(currCount).isEqualTo(count + 1)
    }

}
