package org.acc.task.topping.service.impl

import mu.KotlinLogging
import org.acc.task.topping.dao.model.CustomerModel
import org.acc.task.topping.dao.model.ToppingModel
import org.acc.task.topping.dao.repository.CustomerRepository
import org.acc.task.topping.dao.repository.ToppingRepository
import org.acc.task.topping.dto.SurveyRequestDto
import org.acc.task.topping.dto.ToppingSurveyResultDto
import org.acc.task.topping.dto.ToppingVotesResultDto
import org.acc.task.topping.service.ToppingSurveyService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ToppingSurveyServiceImpl(
    val customerRepository: CustomerRepository,
    val toppingRepository: ToppingRepository
) : ToppingSurveyService {

    val logger = KotlinLogging.logger {}

    @Transactional
    override fun vote(surveyRequest: SurveyRequestDto) {
        logger.info { "Customer ${surveyRequest.email} is voting for toppings ${surveyRequest.toppings}" }

        val customerModel = customerRepository.findById(surveyRequest.email)
            .orElseGet { CustomerModel(surveyRequest.email) }

        customerModel.likedToppings = mapToppings(surveyRequest.toppings)

        customerRepository.save(customerModel)
    }

    @Transactional(readOnly = true)
    override fun getSurveyResults(): ToppingSurveyResultDto {
        logger.info { "Returning toppings survey results" }

        val toppingVotesResultSet = toppingRepository.findAll()
            .mapTo(HashSet()) { ToppingVotesResultDto(it.name, it.customers.size) }

        return ToppingSurveyResultDto(toppingVotesResultSet)
    }

    @Transactional(readOnly = true)
    override fun getSubmittedToppingsForCustomer(email: String): Set<String> {
        logger.info { "Returning toppings survey results for customer $email" }

        return customerRepository.findById(email)
            .orElseThrow { IllegalArgumentException("The customer with email $email didn't vote for toppings") }
            .likedToppings
            .mapTo(HashSet()) { it.name }
    }

    private fun mapToppings(toppings: Set<String>): Set<ToppingModel> {
        return toppings.mapTo(HashSet()) { ToppingModel(it) }
    }

}