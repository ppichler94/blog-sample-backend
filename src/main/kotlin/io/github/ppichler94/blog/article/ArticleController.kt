package io.github.ppichler94.blog.article

import io.github.ppichler94.blog.user.MyUserRepository
import io.github.ppichler94.blog.user.Principal
import jakarta.servlet.http.HttpServletRequest
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class ArticleController(private val repo: ArticleRepository, private val userRepo: MyUserRepository) {

    class ArticleDto(val id: Long?, val title: String, val content: String, val author: String?)

    @PostMapping("/articles")
    fun createArticle(@RequestBody article: ArticleDto, request: HttpServletRequest): ArticleDto {
        val user = userRepo.findByUsername(request.userPrincipal.name)
        Assert.state(user.isPresent, "Unknown user logged in")
        val result = repo.save(Article(null, article.title, article.content, user.get()))
        return ArticleDto(result.id, result.title, result.content, result.author.username)
    }

    @GetMapping("/articles")
    fun getArticles(): Iterable<ArticleDto> {
        return repo.findAll()
            .map { ArticleDto(it.id, it.title, it.content, it.author.username) }
    }

    @GetMapping("/articles/:id")
    fun getArticle(@RequestParam id: Long): Optional<ArticleDto> {
        return repo.findById(id)
            .map { ArticleDto(it.id, it.title, it.content, it.author.username) }
    }
}