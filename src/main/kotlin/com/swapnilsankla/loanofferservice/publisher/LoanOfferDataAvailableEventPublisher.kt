package com.swapnilsankla.loanofferservice.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import com.swapnilsankla.loanofferservice.model.LoanOffer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class LoanOfferDataAvailableEventPublisher(@Autowired val kafkaTemplate: KafkaTemplate<String, String>,
                                           @Autowired val objectMapper: ObjectMapper) {

    fun publish(loanOffer: LoanOffer) {
        Logger.getLogger(LoanOfferDataAvailableEventPublisher::class.simpleName).info("raising event $loanOffer")

            kafkaTemplate.send("loanOfferDataAvailableForLoanProcessing",
                objectMapper.writeValueAsString(loanOffer))
    }
}