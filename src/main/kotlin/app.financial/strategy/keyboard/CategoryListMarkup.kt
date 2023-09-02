package app.financial.strategy.keyboard

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.component.MessageWriter
import app.financial.dto.MarkupDataDto
import app.financial.dto.markup.CategoryListDto
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class CategoryListMarkup(
    private val userRepository: UserRepository,
    private val messageWriter: MessageWriter,
) : InlineKeyboardMarkup<CategoryListDto> {

    override fun isAvailableForCurrentStep(chatId: Long) =
        userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.CATEGORY_LIST

    override fun message(chatId: Long, data: CategoryListDto) =
        messageWriter.process(StepCode.CATEGORY_LIST)

    override fun inlineButtons(chatId: Long, data: CategoryListDto) =
        data.accept
            .mapIndexed {position: Int, buttonText: String -> MarkupDataDto(position, buttonText) }
            .toList()

    override fun getData(chatId: Long) =
        CategoryListDto(listOf("Income operations", "Expense operations"))
}