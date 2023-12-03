package ppichler.blog.user

import jakarta.persistence.*

@Entity
class MyUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(nullable = false)
        var username: String,

        var password: String,
)
