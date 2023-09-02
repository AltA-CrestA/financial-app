package app.financial.strategy.stepper

import org.springframework.stereotype.Component
import app.financial.enums.StepCode

@Component
class AppendStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.APPEND
    }

    override fun getNextStep(chatId: Long, data: String?): StepCode {
        return StepCode.CREATE_TRANSACTION
    }

}