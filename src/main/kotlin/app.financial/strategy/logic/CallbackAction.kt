package app.financial.strategy.logic

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import app.financial.enums.ExecuteStatus

interface CallbackAction : Action {
    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus
}