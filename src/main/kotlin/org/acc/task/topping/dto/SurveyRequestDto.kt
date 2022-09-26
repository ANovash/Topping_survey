package org.acc.task.topping.dto

import org.acc.task.topping.dto.Constraints.EMAIL_REGEX
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SurveyRequestDto(
    @field:NotEmpty
    @field:Size(max = 50)
    @field:Pattern(regexp = EMAIL_REGEX)
    val email: String,

    @field:NotEmpty
    val toppings: Set<@Size(max = 100) String>
)