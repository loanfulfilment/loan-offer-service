package com.swapnilsankla.loanofferservice.publisher

import com.swapnilsankla.loanofferservice.model.LoanOffer
import com.swapnilsankla.tracestarter.CustomKafkaTemplate
import com.swapnilsankla.tracestarter.Trace
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class LoanOfferDataAvailableEventPublisher(@Autowired val kafkaTemplate: CustomKafkaTemplate) {

    private val logger = Logger.getLogger(LoanOfferDataAvailableEventPublisher::class.simpleName)

    fun publish(loanOffer: LoanOffer, trace: Trace) {
        logger.info("raising event $loanOffer")

        kafkaTemplate.publish(topic = "loanOfferDataAvailableForLoanProcessing",
                              data = loanOffer,
                              trace = trace)
    }
}