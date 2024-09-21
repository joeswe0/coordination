package com.commerce.coordination.api

import com.commerce.coordination.api.controller.v1.ApiResponse
import org.apache.coyote.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ApiResponse<Unit> {
        logger.warn(e.message, e)
        return ApiResponse.fail(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(e: Exception): ApiResponse<Unit> {
        logger.error(e.message, e)
        return ApiResponse.error(e.message ?: "에러가 발생했습니다.")
    }
}
