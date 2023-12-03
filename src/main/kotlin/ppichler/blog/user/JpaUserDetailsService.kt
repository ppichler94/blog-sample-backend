package ppichler.blog.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class JpaUserDetailsService(private val userRepo: MyUserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepo.findByUsername(username!!).getOrElse { throw UsernameNotFoundException(username) }
        return Principal(user)
    }
}