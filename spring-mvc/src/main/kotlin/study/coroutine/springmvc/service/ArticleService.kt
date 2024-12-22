package study.coroutine.springmvc.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.coroutine.springmvc.model.Article
import study.coroutine.springmvc.repository.ArticleRepository

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
    fun create(request: ReqCreate): Article {
        return repository.save(Article(
            title = request.title,
            body = request.body,
            authorId = request.authorId,
        ))
    }

    @Transactional
    fun update(id: Long, request: ReqUpdate): Article {
        return repository.findByIdOrNull(id)?.let { article ->
            request.title?.let { article.title = it }
            request.body?.let { article.body = it }
            request.authorId?.let { article.authorId = it }
            article
        } ?: throw NoSuchElementException("No article found (id: $id)")
    }

    @Transactional
    fun delete(id: Long) {
        repository.deleteById(id)
    }

}

data class ReqCreate(
    val title: String,
    val body: String?,
    val authorId: Long?,
)

data class ReqUpdate(
    val title: String?,
    val body: String?,
    val authorId: Long?,
)