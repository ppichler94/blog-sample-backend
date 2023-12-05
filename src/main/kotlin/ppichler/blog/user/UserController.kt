package ppichler.blog.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/api")
class UserController(
        private val repo: MyUserRepository
) {

    @PostMapping("/users")
    fun createUser(@RequestBody user: MyUser): MyUser {
        return repo.save(user)
    }

    @GetMapping("/users")
    fun getUsers(): Iterable<MyUser> {
        return repo.findAll()
    }

    @GetMapping("/users/:id")
    fun getUser(@RequestParam id: Long): Optional<MyUser> {
        return repo.findById(id)
    }
}