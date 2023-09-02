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
class ButtonRequestCommand(
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.BUTTON.command, CommandCode.BUTTON.desc) {

    companion object {
        private val BUTTON_REQUEST = StepCode.CATEGORY_LIST
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        val userModel = userRepository.findByIdOrNull(chatId) ?: throw RuntimeException("User not found")

        userRepository.saveWithNewStepCode(userModel, BUTTON_REQUEST)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = BUTTON_REQUEST)
        )
    }

}
