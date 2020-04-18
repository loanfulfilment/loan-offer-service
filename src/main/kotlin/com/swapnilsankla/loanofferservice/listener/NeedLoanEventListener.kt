package com.swapnilsankla.loanofferservice.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.swapnilsankla.loanofferservice.publisher.LoanOfferDataAvailableEventPublisher
import com.swapnilsankla.loanofferservice.repository.LoanOfferRepository
import com.swapnilsankla.tracestarter.TraceIdExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class NeedLoanEventListener(@Autowired val repository: LoanOfferRepository,
                            @Autowired val objectMapper: ObjectMapper,
                            @Autowired val loanOfferDataAvailableEventPublisher: LoanOfferDataAvailableEventPublisher,
                            @Autowired val traceIdExtractor: TraceIdExtractor) {

    private val logger = Logger.getLogger(NeedLoanEventListener::class.simpleName)

    @KafkaListener(topics = ["needLoanEvent"])
    fun listen(needLoanEventString: String, @Headers headers: Map<String, Any>) {
        val trace = traceIdExtractor.fromKafkaHeaders(headers)

        val needLoanEvent = objectMapper.readValue(needLoanEventString, NeedLoanEvent::class.java)
        logger.info("needLoanEvent event received for customer ${needLoanEvent.customerId}")

        repository
                .findByCustomerId(needLoanEvent.customerId)
                .doOnSuccess { loanOfferDataAvailableEventPublisher.publish(it, trace) }
                .subscribe()
    }
}