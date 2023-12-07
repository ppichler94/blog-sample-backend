package io.github.ppichler94.blog.article

import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
}