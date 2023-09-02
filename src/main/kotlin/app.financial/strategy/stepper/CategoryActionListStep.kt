package app.financial.strategy.stepper

import org.springframework.stereotype.Component
import app.financial.enums.StepCode

@Component
class CategoryActionListStep : ChooseNextStep {
    override fun isAvailableForCurrentStep(stepCode: StepCode) =
        stepCode == StepCode.CATEGORY_ACTION_LIST

    override fun getNextStep(chatId: Long, data: String?) =
        when (data) {
            "Show" -> StepCode.SHOW
            "Append" -> StepCode.APPEND
            else -> null
        }
}