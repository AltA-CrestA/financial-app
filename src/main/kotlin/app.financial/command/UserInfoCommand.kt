package app.financial.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import app.financial.enums.CommandCode
import app.financial.enums.StepCode
import app.financial.event.TelegramStepMessageEvent
import app.financial.lib.repository.UserRepository
import app.financial.lib.repository.saveWithNewStepCode

@Component
class UserInfoCommand(
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.USER_INFO.command, CommandCode.USER_INFO.desc) {

    companion object {
        private val CREATE_TRANSACTION = StepCode.CREATE_TRANSACTION
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        val userModel = userRepository.findByIdOrNull(chatId) ?: throw RuntimeException("User not found")

        userRepository.saveWithNewStepCode(userModel, CREATE_TRANSACTION)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = CREATE_TRANSACTION)
        )
    }

}
