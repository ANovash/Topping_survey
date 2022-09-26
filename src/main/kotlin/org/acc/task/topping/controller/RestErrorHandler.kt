package org.acc.task.topping.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class RestErrorHandler {

    @ExceptionHandler
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class, ConstraintViolationException::class])
    fun handleInputValidationException(ex: Exception): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }


    class ErrorMessage(val statusCode: Int, val errorMessage: String?)

}