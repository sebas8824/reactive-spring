package com.microservices.reactive.service

import com.microservices.reactive.data.Customer
import com.microservices.reactive.data.Customer.Telephone
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl: CustomerService {
    companion object {
        val initialCustomers = arrayOf(Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Microservice", Telephone("+57", "3156161953")))
    }

    val customers = ConcurrentHashMap(initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Int): Mono<Customer> = Mono.justOrEmpty(customers[id])

    override fun searchCustomers(nameFilter: String): Flux<Customer> = customers.filter {
        it.value.name.contains(nameFilter, true)
    }.map(Map.Entry<Int, Customer>::value).toFlux()

    override fun createCustomer(customerMono: Mono<Customer>): Mono<Customer> =
        customerMono.map {
            customers[it.id] = it
            it
        }
}