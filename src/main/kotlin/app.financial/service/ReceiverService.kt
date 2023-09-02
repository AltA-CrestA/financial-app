package app.financial.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import app.financial.event.TelegramReceivedCallbackEvent
import app.financial.event.TelegramReceivedMessageEvent
import app.financial.lib.repository.UserRepository

@Service
class ReceiverService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val userRepository: UserRepository
) {

    fun execute(update: Update) {
        if (update.hasCallbackQuery()) {
            callbackExecute(update.callbackQuery)
        } else if (update.hasMessage()) {
            messageExecute(update.message)
        } else {
            throw IllegalStateException("Not yet supported")
        }
    }

    private fun messageExecute(message: Message) {
        val chatId = message.chatId
        val stepCode = userRepository.findByIdOrNull(chatId)!!.stepCode
        applicationEventPublisher.publishEvent(
            TelegramReceivedMessageEvent(
                chatId = chatId,
                stepCode = stepCode,
                message = message
            )
        )
    }

    private fun callbackExecute(callback: CallbackQuery) {
        val chatId = callback.from.id
        val stepCode = userRepository.findByIdOrNull(chatId)!!.stepCode
        applicationEventPublisher.publishEvent(
            TelegramReceivedCallbackEvent(chatId = chatId, stepCode = stepCode, callback = callback)
        )
    }
}