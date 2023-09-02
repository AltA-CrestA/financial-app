package app.financial.dto

data class InlineKeyboardMarkupDto(
    /** Сообщение */
    val message: String,
    /** Кнопки под сообщением */
    val inlineButtons: List<MarkupDataDto>
)
