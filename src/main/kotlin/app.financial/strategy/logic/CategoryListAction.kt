package app.financial.strategy.logic

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import app.financial.enums.ExecuteStatus
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class CategoryListAction(private val userRepository: UserRepository) : CallbackAction {

    override fun execute(chatId: Long, callbackQuery: CallbackQuery) = ExecuteStatus.FINAL

    override fun isAvailableForCurrentStep(chatId: Long) =
        userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.CATEGORY_LIST

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}