package io.github.ppichler94.blog.article

import io.github.ppichler94.blog.user.MyUserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.EntityLinks
import org.springframework.hateoas.server.ExposesResourceFor
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/articles")
@ExposesResourceFor(ArticleController.ArticleDto::class)
class ArticleController(
    private val repo: ArticleRepository,
    private val userRepo: MyUserRepository,
    private val entityLinks: EntityLinks
) {

    class ArticleDto(val id: Long?, val title: String, val content: String, val author: String?)

    @PostMapping
    fun createArticle(@RequestBody article: ArticleDto, request: HttpServletRequest): ArticleDto {
        val user = userRepo.findByUsername(request.userPrincipal.name)
        Assert.state(user.isPresent, "Unknown user logged in")
        val result = repo.save(Article(null, article.title, article.content, user.get()))
        return ArticleDto(result.id, result.title, result.content, result.author.username)
    }

    @GetMapping
    fun getArticles(): CollectionModel<ArticleDto> {
        val articles = repo.findAll()
            .map { ArticleDto(it.id, it.title, it.content, it.author.username) }
        return CollectionModel.of(articles, entityLinks.linkFor(ArticleDto::class.java).withSelfRel())
    }

    @GetMapping("{id}")
    fun getArticle(@PathVariable id: Long): EntityModel<ArticleDto> {
        val article = repo.findById(id)
            .map { ArticleDto(it.id, it.title, it.content, it.author.username) }
        return EntityModel.of(
            article.orElseThrow(),
            entityLinks.linkToItemResource(ArticleDto::class.java, id).withSelfRel(),
            entityLinks.linkFor(ArticleDto::class.java).withRel("articles")
        )
    }
}