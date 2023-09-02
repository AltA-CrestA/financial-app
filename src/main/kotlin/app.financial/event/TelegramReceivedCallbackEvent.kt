package app.financial.event

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import app.financial.enums.StepCode

class TelegramReceivedCallbackEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val callback: CallbackQuery
)
