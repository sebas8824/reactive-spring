package com.microservices.reactive.mongo

import com.microservices.reactive.data.Customer
import com.microservices.reactive.data.Customer.Telephone
import com.microservices.reactive.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var mongoOperations: ReactiveMongoOperations

    private val COLLECTION = "Customers"

    companion object {
        val initialCustomers = listOf(
                Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Reactive", Telephone("+44", "1234567890"))
        )
    }

    @PostConstruct
    fun initData() {
        mongoOperations.collectionExists(COLLECTION).subscribe {
            if(it != true) mongoOperations.createCollection(COLLECTION).subscribe {
                println("$COLLECTION collection created")
            } else println("$COLLECTION collection already exists")

            customerRepository.saveAll(initialCustomers).subscribe {
                // Sorry for this callback hell
                customerRepository.save(Customer(4, "Microservices")).subscribe {
                    println("Default customers created!")
                }
            }

        }
    }
}