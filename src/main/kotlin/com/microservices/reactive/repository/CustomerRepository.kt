package com.microservices.reactive.repository

import com.microservices.reactive.data.Customer
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CustomerRepository: ReactiveCrudRepository<Customer, Int>