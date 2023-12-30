package io.github.ppichler94.blog.article

import io.github.ppichler94.blog.user.MyUserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.EntityLinks
import org.springframework.hateoas.server.ExposesResourceFor
import org.springframework.http.HttpStatus
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/articles")
@ExposesResourceFor(ArticleController.ArticleDto::class)
class ArticleController(
    private val repo: ArticleRepository,
    private val userRepo: MyUserRepository,
    private val entityLinks: EntityLinks
) {

    class ArticleDto(val id: Long?, val title: String, val content: String, val author: String?) :
        RepresentationModel<ArticleDto>()

    fun Article.toDto(): ArticleDto {
        val dto = ArticleDto(id, title, content, author.username)
        return dto.apply {
            add(entityLinks.linkToItemResource(ArticleDto::class.java, id!!).withSelfRel())
            add(entityLinks.linkFor(ArticleDto::class.java).withRel("articles"))
        }
    }

    @PostMapping
    fun createArticle(@RequestBody article: ArticleDto, request: HttpServletRequest): ArticleDto {
        val user = userRepo.findByUsername(request.userPrincipal.name)
        Assert.state(user.isPresent, "Unknown user logged in")
        val result = repo.save(Article(null, article.title, article.content, user.get()))
        return result.toDto()
    }

    @GetMapping
    fun getArticles(): CollectionModel<ArticleDto> {
        val articles = repo.findAll()
            .map { it.toDto() }
        return CollectionModel.of(articles, entityLinks.linkFor(ArticleDto::class.java).withSelfRel())
    }

    @GetMapping("{id}")
    fun getArticle(@PathVariable id: Long): ArticleDto {
        val article = repo.findById(id)
        return article.map { it.toDto() }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }
}