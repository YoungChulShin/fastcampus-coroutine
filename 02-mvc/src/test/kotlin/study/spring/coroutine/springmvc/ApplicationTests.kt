package study.spring.coroutine.springmvc

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.spring.coroutine.springmvc.model.Article
import study.spring.coroutine.springmvc.repository.ArticleRepository

private val logger = KotlinLogging.logger {  }

@SpringBootTest
class ApplicationTests(
    @Autowired private val repository: ArticleRepository,
) {

    // 23분 
    @Test
    fun contextLoads() {
        var prev = repository.count()
        repository.save(Article(
            title = "title",
            body = "body",
            authorId = 1234
        )).let { logger.debug { it } }
        var curr = repository.count()
        logger.debug { ">> prev: $prev, curr: $curr" }
    }

}
