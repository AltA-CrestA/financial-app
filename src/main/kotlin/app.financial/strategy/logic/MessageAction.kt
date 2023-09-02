package app.financial.strategy.logic

import org.telegram.telegrambots.meta.api.objects.Message

interface MessageAction: Action {
    fun execute(chatId: Long, message: Message)
}