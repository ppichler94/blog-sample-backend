package ppichler.blog.user

import org.springframework.data.repository.CrudRepository
import java.util.*

interface MyUserRepository: CrudRepository<MyUser, Long> {
    fun findByUsername(username: String): Optional<MyUser>
    fun save(user: MyUser): MyUser
}