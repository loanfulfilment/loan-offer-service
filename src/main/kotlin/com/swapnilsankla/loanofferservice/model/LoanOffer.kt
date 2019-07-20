package com.swapnilsankla.loanofferservice.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("offers")
data class LoanOffer(val customerId: String, val offer: Offer)

data class Offer(val interestRate: Double,
                 val tenureInMonths: Int,
                 val approvedLoanAmount: Double,
                 val foreclosureCharges: Double,
                 val partPaymentCharges: Double)