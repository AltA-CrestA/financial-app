package app.financial.strategy.keyboard

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import app.financial.component.MessageWriter
import app.financial.dto.MarkupDataDto
import app.financial.dto.markup.CategoryActionListDto
import app.financial.enums.StepCode
import app.financial.lib.repository.UserRepository

@Component
class CategoryActionListMarkup(
    private val userRepository: UserRepository,
    private val messageWriter: MessageWriter
) : InlineKeyboardMarkup<CategoryActionListDto> {
    override fun isAvailableForCurrentStep(chatId: Long) =
        userRepository.findByIdOrNull(chatId)!!.stepCode == StepCode.CATEGORY_ACTION_LIST

    override fun message(chatId: Long, data: CategoryActionListDto) =
        messageWriter.process(StepCode.CATEGORY_ACTION_LIST)

    override fun inlineButtons(chatId: Long, data: CategoryActionListDto) =
        data.accept
            .mapIndexed { posNumber, buttonText -> MarkupDataDto(posNumber, buttonText) }
            .toList()

    override fun getData(chatId: Long) = CategoryActionListDto(listOf("Show", "Append"))
}