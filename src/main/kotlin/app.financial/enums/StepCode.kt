package app.financial.enums

enum class StepCode(val type: StepType, val botPause: Boolean) {
    START(StepType.SIMPLE_TEXT, false),
    CREATE_TRANSACTION(StepType.SIMPLE_TEXT, true),
    CATEGORY_LIST(StepType.INLINE_KEYBOARD_MARKUP, true),
    CATEGORY_ACTION_LIST(StepType.INLINE_KEYBOARD_MARKUP, true),
    SHOW(StepType.SIMPLE_TEXT, true),
    APPEND(StepType.SIMPLE_TEXT, true),
}

enum class StepType {
    SIMPLE_TEXT,
    INLINE_KEYBOARD_MARKUP
}