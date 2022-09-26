package org.acc.task.topping.controller

import org.acc.task.topping.dto.Constraints.EMAIL_REGEX
import org.acc.task.topping.dto.SurveyRequestDto
import org.acc.task.topping.dto.ToppingSurveyResultDto
import org.acc.task.topping.service.ToppingSurveyService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Pattern

@Validated
@RestController
@RequestMapping("/survey")
class ToppingSurveyController(
    val toppingSurveyService: ToppingSurveyService
) {

    @PutMapping
    fun vote(@Valid @RequestBody surveyRequest: SurveyRequestDto) {
        toppingSurveyService.vote(surveyRequest)
    }

    @GetMapping
    fun getSurveyResults(): ToppingSurveyResultDto =
        toppingSurveyService.getSurveyResults()

    @GetMapping("/{email}")
    fun getSubmittedToppingsForCustomer(@Valid @Pattern(regexp = EMAIL_REGEX) @PathVariable email: String): Set<String> =
        toppingSurveyService.getSubmittedToppingsForCustomer(email)

}