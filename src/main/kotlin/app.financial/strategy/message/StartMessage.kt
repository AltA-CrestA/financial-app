package app.financial.strategy.message

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.component.MessageWriter
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class StartMessage(
    private val userRepository: UserRepository,
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.START
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.START)
    }
}