package app.financial.strategy.stepper

import app.financial.enums.StepCode

interface ChooseNextStep {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun getNextStep(chatId: Long, data: String? = null): StepCode?
}
