package app.financial.strategy.keyboard

import app.financial.dto.MarkupDataDto
import app.financial.dto.markup.DataModel

interface InlineKeyboardMarkup<T: DataModel> {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun message(chatId: Long, data: T): String

    fun inlineButtons(chatId: Long, data: T): List<MarkupDataDto>

    fun getData(chatId: Long): T
}
