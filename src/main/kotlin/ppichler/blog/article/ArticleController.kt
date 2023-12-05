package ppichler.blog.article

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

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