package com.swapnilsankla.loanofferservice.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.swapnilsankla.loanofferservice.publisher.LoanOfferDataAvailableEventPublisher
import com.swapnilsankla.loanofferservice.repository.LoanOfferRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class NeedLoanEventListener(@Autowired val repository: LoanOfferRepository,
                            @Autowired val objectMapper: ObjectMapper,
                            @Autowired val loanOfferDataAvailableEventPublisher: LoanOfferDataAvailableEventPublisher) {

    @KafkaListener(topics = ["needLoanEvent"])
    fun listen(needLoanEventString: String) {
        val needLoanEvent = objectMapper.readValue(needLoanEventString, NeedLoanEvent::class.java)
        Logger.getLogger(NeedLoanEventListener::class.simpleName)
                .info("needLoanEvent event received for customer ${needLoanEvent.customerId}")

        repository
                .findByCustomerId(needLoanEvent.customerId)
                .doOnSuccess(loanOfferDataAvailableEventPublisher::publish)
                .subscribe()
    }
}