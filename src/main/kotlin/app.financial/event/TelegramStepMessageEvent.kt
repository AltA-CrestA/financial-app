package app.financial.event

import app.financial.enums.StepCode

class TelegramStepMessageEvent(
    val chatId: Long,
    val stepCode: StepCode
)
