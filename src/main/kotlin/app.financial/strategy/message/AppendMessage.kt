package app.financial.strategy.message

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.component.MessageWriter
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class AppendMessage(
    private val userRepository: UserRepository,
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.APPEND
    }

    override fun getMessage(chatId: Long): String {
        return "Enter an amount:"
    }
}
