package app.financial.strategy.message

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.component.MessageWriter
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class ShowMessage(
    private val userRepository: UserRepository,
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.SHOW
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.SHOW, mapOf(
            "type"   to "Income",
            "amount" to userRepository.findByIdOrNull(chatId)!!.incomeList.sumOf { it.amount }.toString()
        ))
    }
}
