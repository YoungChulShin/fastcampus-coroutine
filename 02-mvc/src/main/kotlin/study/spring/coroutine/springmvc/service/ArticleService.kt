package study.spring.coroutine.springmvc.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import study.spring.coroutine.springmvc.model.Article
import study.spring.coroutine.springmvc.repository.ArticleRepository
import java.util.NoSuchElementException

@Service
class ArticleService(
    private val repository: ArticleRepository,
) {

    fun get(id: Long): Article {
        return repository.findByIdOrNull(id) ?: throw NoSuchElementException("No article found (id: $id)")
    }

    fun getAll(title: String?): List<Article> {
        return if (title.isNullOrEmpty()) {
            repository.findAll()
        } else {
            repository.findAllByTitleContains("%$title%")
        }
    }

    @Transactional
    fun create(request: RequestCreate):Article {
        return repository.save(Article(
            title = request.title,
            body = request.body,
            authorId =  request.authorId,
        ))
    }

    // 34
    fun update(id: Long, request: RequestUpate) {
        repository.findByIdOrNull(id)?.let {

        }
    }
}

data class RequestCreate(
    val title: String,
    val body: String?,
    val authorId: Long?,
)

data class RequestUpate(
    val title: String?,
    val body: String?,
    val authorId: Long?,
)