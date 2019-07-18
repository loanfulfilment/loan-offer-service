package com.swapnilsankla.loanofferservice.repository

import com.swapnilsankla.loanofferservice.model.LoanOffer
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface LoanOfferRepository: ReactiveMongoRepository<LoanOffer, String> {
    fun findByCustomerId(customerId: String): Mono<LoanOffer>
}