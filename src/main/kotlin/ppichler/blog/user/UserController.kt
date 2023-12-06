package ppichler.blog.user

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class UserController(
    private val repo: MyUserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @PostMapping("/users")
    fun createUser(@RequestBody user: MyUser): MyUser {
        return repo.save(MyUser(null, user.username, passwordEncoder.encode(user.password)))
    }

    @GetMapping("/users")
    fun getUsers(): Iterable<MyUser> {
        return repo.findAll()
    }

    @GetMapping("/users/:id")
    fun getUser(@RequestParam id: Long): Optional<MyUser> {
        return repo.findById(id)
    }

    @GetMapping("/user")
    fun getCurrentUser(request: HttpServletRequest): String {
        return request.userPrincipal.name
    }
}