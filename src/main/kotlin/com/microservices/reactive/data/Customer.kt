package com.microservices.reactive.data

data class Customer(var id: Int = 0, val name: String = "", val telephone: Telephone? = null) {
    data class Telephone(var countryCode: String = "", var telephoneNumber: String = "")
}