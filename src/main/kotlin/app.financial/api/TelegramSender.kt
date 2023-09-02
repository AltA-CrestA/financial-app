package app.financial.api

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import app.financial.properties.BotProperty
import app.financial.service.ReceiverService

@Component
class TelegramSender(
    private val botProperty: BotProperty,
    botCommands: List<IBotCommand>,
    private val receiverService: ReceiverService
) : TelegramLongPollingCommandBot(botProperty.token) {

    init {
        registerAll(*botCommands.toTypedArray())

        super.registerDefaultAction { absSender, message ->

            val commandUnknownMessage = SendMessage()
            commandUnknownMessage.chatId = message.chatId.toString()
            commandUnknownMessage.text = "Command '" + message.text.toString() + "' unknown"

            absSender.execute(commandUnknownMessage)
        }
    }

    override fun getBotUsername() = botProperty.username

    override fun processNonCommandUpdate(update: Update) {
        receiverService.execute(update)
    }
}