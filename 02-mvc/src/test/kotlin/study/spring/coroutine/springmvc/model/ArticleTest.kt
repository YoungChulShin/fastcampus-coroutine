package study.spring.coroutine.springmvc.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ArticleTest {

    @Test
    fun printArticle() {
        Article(1, "titile", "body", 2).apply {
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
        }.let { println(">> article: $it") }
    }
}