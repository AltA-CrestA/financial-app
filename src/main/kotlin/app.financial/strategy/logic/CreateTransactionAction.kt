package app.financial.strategy.logic

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import app.financial.enums.StepCode
import app.financial.lib.models.Income
import app.financial.lib.repository.IncomeRepository
import app.financial.lib.repository.UserRepository

@Component
class CreateTransactionAction(
    private val userRepository: UserRepository,
    private val incomeRepository: IncomeRepository,
) : MessageAction {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        // TODO: поменять степ
        return userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.APPEND
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun execute(chatId: Long, message: Message) {
        val amount = message.text.toBigDecimal()
        val income = Income(amount)
        income.user = userRepository.findByIdOrNull(chatId)!!
        incomeRepository.save(income)
    }
}