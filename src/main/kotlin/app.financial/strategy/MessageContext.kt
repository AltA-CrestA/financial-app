package app.financial.strategy

import org.springframework.stereotype.Component
import app.financial.strategy.message.Message

@Component
class MessageContext(private val message: List<Message>) {

    fun getMessage(chatId: Long): String {
        return message
            .filter { it.isAvailableForCurrentStep(chatId) }
            .map { it.getMessage(chatId) }
            .first()
    }
}
