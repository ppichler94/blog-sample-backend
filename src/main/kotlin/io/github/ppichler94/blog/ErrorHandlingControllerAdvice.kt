package io.github.ppichler94.blog

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandlingControllerAdvice {

    @ExceptionHandler
    fun handler(ise: IllegalStateException): ProblemDetail {
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value()).apply {
            detail = ise.localizedMessage
        }
    }
}