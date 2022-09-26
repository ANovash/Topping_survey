package org.acc.task.topping.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.acc.task.topping.dao.model.CustomerModel
import org.acc.task.topping.dao.model.ToppingModel
import org.acc.task.topping.dao.repository.CustomerRepository
import org.acc.task.topping.dao.repository.ToppingRepository
import org.acc.task.topping.dto.SurveyRequestDto
import org.acc.task.topping.service.ToppingSurveyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class ToppingSurveyServiceImplTests {

    val customerRepository: CustomerRepository = mockk()
    val toppingRepository: ToppingRepository = mockk()

    val service: ToppingSurveyService = ToppingSurveyServiceImpl(customerRepository, toppingRepository)


    @Test
    fun whenNewCustomerVotesForTopping_thenSaveCustomerAndTopping() {
        // given
        val customer = slot<CustomerModel>()

        every { customerRepository.findById(any()) } returns Optional.empty()
        every { customerRepository.save(capture(customer)) } returns mockk()

        val surveyRequest = SurveyRequestDto("newcustomer@email.com", setOf("Topping1", "Topping2"))

        // when
        service.vote(surveyRequest)

        // then
        verify(exactly = 1) { customerRepository.findById("newcustomer@email.com") }
        verify(exactly = 1) { customerRepository.save(any()) }

        assert(customer.captured.email == "newcustomer@email.com")
        assert(customer.captured.likedToppings.size == 2)
        assert(customer.captured.likedToppings.map { it.name }.containsAll(setOf("Topping1", "Topping2")))
    }

    @Test
    fun whenExistingCustomerVotesForTopping_thenUpdateToppingsForCustomer() {
        // given
        val customer = CustomerModel("customer@email.com")
        customer.likedToppings = setOf(ToppingModel("Topping1"), ToppingModel("Topping2"))

        every { customerRepository.findById("customer@email.com") } returns Optional.of(customer)
        every { customerRepository.save(customer) } returns customer

        val surveyRequest = SurveyRequestDto("customer@email.com", setOf("Topping3", "Topping2"))

        // when
        service.vote(surveyRequest)

        // then
        verify(exactly = 1) { customerRepository.findById("customer@email.com") }
        verify(exactly = 1) { customerRepository.save(customer) }

        assert(customer.email == "customer@email.com")
        assert(customer.likedToppings.size == 2)
        assert(customer.likedToppings.map { it.name }.containsAll(setOf("Topping2", "Topping3")))
    }

    @Test
    fun whenRetrieveToppingsForNotExistingCustomer_thenThrowIllegalArgumentException() {
        // given
        every { customerRepository.findById(any()) } returns Optional.empty()

        // when
        val exc = assertThrows<IllegalArgumentException> {
            service.getSubmittedToppingsForCustomer("customer@email.com")
        }

        // then
        verify(exactly = 1) { customerRepository.findById("customer@email.com") }
        assert(exc.message == "The customer with email customer@email.com didn't vote for toppings")
    }

}