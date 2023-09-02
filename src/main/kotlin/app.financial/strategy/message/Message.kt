package app.financial.strategy.message

interface Message {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun getMessage(chatId: Long): String
}
