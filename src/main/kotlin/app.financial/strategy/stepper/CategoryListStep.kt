package app.financial.strategy.stepper

import org.springframework.stereotype.Component
import app.financial.enums.StepCode

@Component
class CategoryListStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.CATEGORY_LIST
    }

    override fun getNextStep(chatId: Long, data: String?): StepCode {
        return StepCode.CATEGORY_ACTION_LIST
    }

}