package app.financial.strategy

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import app.financial.enums.ExecuteStatus
import app.financial.strategy.logic.CallbackAction
import app.financial.strategy.logic.Action
import app.financial.strategy.logic.MessageAction

@Component
class LogicContext(private val action: List<Action>) {

    fun execute(chatId: Long, message: Message) {
        action.filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .forEach {
                (it as MessageAction).execute(chatId = chatId, message = message)
            }
    }

    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        return action
            .filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .map { (it as CallbackAction).execute(chatId = chatId, callbackQuery = callbackQuery) }
            .first()
    }
}
