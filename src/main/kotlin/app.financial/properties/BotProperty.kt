package app.financial.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "telegrambots")
data class BotProperty(
    var username: String = "",
    var token: String = ""
)
