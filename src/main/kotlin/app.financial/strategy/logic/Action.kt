package app.financial.strategy.logic

interface Action {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun isPermitted(chatId: Long): Boolean

}
