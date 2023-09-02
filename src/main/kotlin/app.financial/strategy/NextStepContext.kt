package app.financial.strategy

import org.springframework.stereotype.Component
import app.financial.enums.StepCode
import app.financial.strategy.stepper.ChooseNextStep

@Component
class NextStepContext(private val chooseNextStep: List<ChooseNextStep>) {

    fun next(chatId: Long, stepCode: StepCode): StepCode? {
        return chooseNextStep.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getNextStep(chatId)
    }

    fun next(chatId: Long, stepCode: StepCode, data: String): StepCode? {
        return chooseNextStep.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getNextStep(chatId, data)
    }

}
