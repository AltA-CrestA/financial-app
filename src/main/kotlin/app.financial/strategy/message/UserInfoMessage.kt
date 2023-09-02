package app.financial.strategy.message

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.component.MessageWriter
import app.financial.dto.UserInfoDto
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class UserInfoMessage(
    private val userRepository: UserRepository,
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.CREATE_TRANSACTION
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.CREATE_TRANSACTION, UserInfoDto(chatId))
    }
}