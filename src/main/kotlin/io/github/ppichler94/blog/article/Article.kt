package io.github.ppichler94.blog.article

import io.github.ppichler94.blog.user.MyUser
import jakarta.persistence.*

@Entity
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,
    var content: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id",)
    var author: MyUser
)
