package study.spring.coroutine.springmvc.repository

import org.springframework.data.jpa.repository.JpaRepository
import study.spring.coroutine.springmvc.model.Article

interface ArticleRepository: JpaRepository<Article, Long> {

    fun findAllByTitleContains(title: String): List<Article>
}