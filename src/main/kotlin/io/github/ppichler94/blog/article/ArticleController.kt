package io.github.ppichler94.blog.article

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class ArticleController(private val repo: ArticleRepository) {

    @PostMapping("/articles")
    fun createArticle(@RequestBody article: Article): Article {
        return repo.save(article)
    }

    @GetMapping("/articles")
    fun getArticles(): Iterable<Article> {
        return repo.findAll()
    }

    @GetMapping("/articles/:id")
    fun getArticle(@RequestParam id: Long): Optional<Article> {
        return repo.findById(id)
    }
}