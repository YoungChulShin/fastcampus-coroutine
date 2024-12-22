package study.coroutine.springmvc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import study.coroutine.springmvc.model.Article

@Repository
interface ArticleRepository: JpaRepository<Article, Long> {

    fun findAllByTitleContains(title: String): List<Article>
}