package study.coroutine.webfluxreactor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import study.coroutine.webfluxreactor.exception.NoArticleException
import study.coroutine.webfluxreactor.model.Article
import study.coroutine.webfluxreactor.repository.ArticleRepository

@Service
class ArticleService(
    private val respository: ArticleRepository,
) {

    @Transactional
    fun create(request: ReqCreate): Mono<Article> {
        return respository.save(request.toArticle())
    }

    fun get(id: Long): Mono<Article> {
        return respository.findById(id)
            .switchIfEmpty { throw NoArticleException("id: $id") }
    }
}


data class ReqCreate(
    val title: String,
    val body: String? = null,
    val authorId: Long? = null,
) {
    fun toArticle(): Article {
        return Article(
            title = this.title,
            body = this.body,
            authorId = this.authorId)
    }
}