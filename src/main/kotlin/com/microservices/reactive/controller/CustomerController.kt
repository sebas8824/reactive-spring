package com.microservices.reactive.controller

import com.microservices.reactive.data.Customer
import com.microservices.reactive.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CustomerController {

    @Autowired
    private lateinit var service: CustomerService

    @GetMapping("/customer/{id}")
    fun getCustomer(@PathVariable id: Int): ResponseEntity<Mono<Customer>> {
        val customer = service.getCustomer(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping("/customers")
    fun getCustomers(@RequestParam(required=false, defaultValue="") nameFilter: String): ResponseEntity<Flux<Customer>> {
        val customers = service.searchCustomers(nameFilter)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @PostMapping("/customer/")
    fun createCustomer(@RequestBody customerMono: Mono<Customer>) =
            ResponseEntity(service.createCustomer(customerMono), HttpStatus.CREATED)
}