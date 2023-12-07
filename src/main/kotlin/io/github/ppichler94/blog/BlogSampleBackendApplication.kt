package io.github.ppichler94.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogSampleBackendApplication

fun main(args: Array<String>) {
    runApplication<BlogSampleBackendApplication>(*args)
}
