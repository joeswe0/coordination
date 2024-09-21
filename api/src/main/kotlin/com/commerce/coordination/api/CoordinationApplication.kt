package com.commerce.coordination.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.commerce.coordination"])
@SpringBootApplication
class CoordinationApplication

fun main(args: Array<String>) {
    runApplication<CoordinationApplication>(*args)
}
