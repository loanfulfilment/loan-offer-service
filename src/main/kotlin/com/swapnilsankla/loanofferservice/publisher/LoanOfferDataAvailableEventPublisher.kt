package com.swapnilsankla.loanofferservice.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import com.swapnilsankla.loanofferservice.model.LoanOffer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class LoanOfferDataAvailableEventPublisher(@Autowired val kafkaTemplate: KafkaTemplate<String, String>,
                                           @Autowired val objectMapper: ObjectMapper) {

    private val logger = Logger.getLogger(LoanOfferDataAvailableEventPublisher::class.simpleName)

    fun publish(loanOffer: LoanOffer, traceId: ByteArray) {
        logger.info("raising event $loanOffer")

        kafkaTemplate.send(buildMessage(loanOffer, traceId))
    }

    private fun buildMessage(loanOffer: LoanOffer, traceId: ByteArray): ProducerRecord<String, String> {
        val message = ProducerRecord<String, String>(
                "loanOfferDataAvailableForLoanProcessing",
                objectMapper.writeValueAsString(loanOffer)
        )

        message.headers().remove("uber-trace-id")
        message.headers().add("uber-trace-id", traceId)

        return message
    }
}