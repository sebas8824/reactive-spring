package com.microservices.reactive.exception

class CustomerExistsException(override val message: String): Exception(message)