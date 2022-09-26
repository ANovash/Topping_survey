package org.acc.task.topping.service

import org.acc.task.topping.dto.SurveyRequestDto
import org.acc.task.topping.dto.ToppingSurveyResultDto

interface ToppingSurveyService {

    fun vote(surveyRequest: SurveyRequestDto)

    fun getSurveyResults(): ToppingSurveyResultDto

    fun getSubmittedToppingsForCustomer(email: String): Set<String>

}