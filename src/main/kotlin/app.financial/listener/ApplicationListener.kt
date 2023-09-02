package app.financial.listener

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.enums.ExecuteStatus
import app.financial.event.TelegramReceivedCallbackEvent
import app.financial.event.TelegramReceivedMessageEvent
import app.financial.event.TelegramStepMessageEvent
import app.financial.lib.repository.UserRepository
import app.financial.lib.repository.saveWithNewStepCode
import app.financial.service.MessageService
import app.financial.strategy.LogicContext
import app.financial.strategy.NextStepContext

@Component
class ApplicationListener(
    private val logicContext: LogicContext,
    private val nextStepContext: NextStepContext,
    private val userRepository: UserRepository,
    private val messageService: MessageService
) {

    inner class StepMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramStepMessageEvent) {
            with(userRepository) {
                this.saveWithNewStepCode(
                    this.findByIdOrNull(event.chatId)!!,
                    event.stepCode,
                )
            }

            messageService.sendMessageToBot(event.chatId, event.stepCode)
        }
    }

    inner class Message {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedMessageEvent) {
            logicContext.execute(event.chatId, event.message)
            nextStepContext.next(event.chatId, event.stepCode)?.let {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = it
                    )
                )
            }
        }
    }

    inner class CallbackMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedCallbackEvent) {
            when (logicContext.execute(event.chatId, event.callback)) {
                ExecuteStatus.FINAL -> {
                    nextStepContext.next(event.chatId, event.stepCode)
                }
                ExecuteStatus.NOTHING -> {
                    nextStepContext.next(event.chatId, event.stepCode, event.callback.data)
                }
            }?.let {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = it
                    )
                )
            }
        }
    }

    @Bean
    @Lazy
    fun messageBean(): Message = Message()

    @Bean
    @Lazy
    fun stepMessageBean(): StepMessage = StepMessage()

    @Bean
    @Lazy
    fun callbackMessageBean(): CallbackMessage = CallbackMessage()

}