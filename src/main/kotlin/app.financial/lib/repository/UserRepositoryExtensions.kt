package app.financial.lib.repository

import app.financial.enums.StepCode
import app.financial.lib.models.User

fun UserRepository.saveWithNewStepCode(user: User, stepCode: StepCode): User {
    user.stepCode = stepCode
    return this.save(user)
}