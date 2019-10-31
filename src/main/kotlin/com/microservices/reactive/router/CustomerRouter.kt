package com.microservices.reactive.router

import com.microservices.reactive.handler.CustomerHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.router

/*
* Routers: Handle the paths and verbs that our reactive service will answer
* Hanlers: Perform the logic to transform a concrete request into a response
* Services: Encapsulate the business logic of our domain
* */

@Component
class CustomerRouter(private val customerHandler: CustomerHandler) {

    @Bean
    fun customerRoutes()= router {
        "/customer".nest {
            GET("/{id}", customerHandler::get)
            POST("/", customerHandler::create)
            DELETE("/{id}", customerHandler::delete)
        }
        "/customers".nest {
            GET("/", customerHandler::search)
        }
    }
}

