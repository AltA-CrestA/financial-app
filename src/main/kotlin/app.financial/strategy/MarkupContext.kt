package app.financial.strategy


import org.springframework.stereotype.Component
import app.financial.dto.InlineKeyboardMarkupDto
import app.financial.dto.markup.DataModel
import app.financial.strategy.keyboard.InlineKeyboardMarkup

@Component
class MarkupContext<T : DataModel>(private val inlineKeyboardMarkup: List<InlineKeyboardMarkup<T>>) {

    fun getInlineKeyboardMarkupDto(
        chatId: Long
    ): InlineKeyboardMarkupDto? {
        return inlineKeyboardMarkup
            .firstOrNull { it.isAvailableForCurrentStep(chatId) }
            ?.let {
                val data = it.getData(chatId)
                InlineKeyboardMarkupDto(
                    it.message(chatId, data),
                    it.inlineButtons(chatId, data)
                )
            }
    }
}
