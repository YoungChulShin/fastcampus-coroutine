package study.coroutine.springmvc.controller

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.*
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.*
import study.coroutine.springmvc.service.ArticleService
import study.coroutine.springmvc.service.ReqCreate

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:db-init/test.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ArticleControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val articeService: ArticleService,
) {

    @Autowired
    private lateinit var articleService: ArticleService

    @Test
    fun get() {
        mockMvc.get("/article/1") {
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("title") {
                value("title 1")
            }
        }

    }

    @Test
    fun getAll() {
        mockMvc.get("/article/all") {
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.length()") {
                value(1)
            }
        }
    }

    @Test
    fun create() {
        mockMvc.post("/article") {
            contentType = APPLICATION_JSON
            content = """
                {
                    "title": "title create",
                    "body": "body create",
                    "authorId": 9999
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("title") { value("title create") }
            jsonPath("body") { value("body create") }
        }
    }

    @Test
    fun update() {
        mockMvc.put("/article/1") {
            contentType = APPLICATION_JSON
            content = """
                {
                    "title": "title update"
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("title") { value("title update") }
        }
    }

    @Test
    fun delete() {
        val create = articeService.create(
            ReqCreate(
                title = "title",
                body = "body",
                authorId = 1234
            )
        )
        val prev = articeService.getAll().size

        mockMvc.delete("/article/${create.id}") {
            contentType = APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

        val curr = articleService.getAll().size

        Assertions.assertThat(curr).isEqualTo(prev - 1)
    }
}