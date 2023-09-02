package app.financial.event

import org.telegram.telegrambots.meta.api.objects.Message
import app.financial.enums.StepCode

class TelegramReceivedMessageEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val message: Message
)
