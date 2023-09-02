package app.financial.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User as TgUser
import org.telegram.telegrambots.meta.bots.AbsSender
import app.financial.enums.CommandCode
import app.financial.enums.StepCode
import app.financial.event.TelegramStepMessageEvent
import app.financial.lib.models.User
import app.financial.lib.repository.UserRepository
import app.financial.lib.repository.saveWithNewStepCode
import app.financial.service.MessageService

@Component
class StartCommand(
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.START.command, CommandCode.START.desc) {

    companion object {
        private val START_CODE = StepCode.START
    }

    override fun execute(absSender: AbsSender, user: TgUser, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        val userModel = userRepository.findByIdOrNull(chatId)
            ?: User(chatId, START_CODE)

        userRepository.saveWithNewStepCode(userModel, START_CODE)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = START_CODE)
        )
    }
}
