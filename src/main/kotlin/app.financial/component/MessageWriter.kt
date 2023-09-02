package app.financial.component

import java.io.InputStreamReader
import java.io.StringWriter
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import app.financial.enums.StepCode

@Component
class MessageWriter(
    private val freeMarkerConfigurer: FreeMarkerConfigurer,
    private val resources: ResourceLoader
) {

    fun process(stepCode: StepCode, formatType: Any? = null): String {
        val name = stepCode.name.lowercase().replace("_", "-")
        return when (formatType) {
            null -> getMessage("$name.html")
            else -> processed(mapOf("data" to formatType), "$name.ftl")
        }
    }

    private fun processed(data: Map<String, Any>, templateName: String): String {
        val template = freeMarkerConfigurer.configuration.getTemplate(templateName)
        val output = StringWriter()
        template.process(data, output)
        return output.toString()
    }

    private fun getMessage(htmlFileName: String): String {
        val resource = resources.getResource("classpath:message/simple/$htmlFileName")
        if (resource.exists()) {
            return InputStreamReader(resource.inputStream).use { it.readText() }
        }
        return "SAMPLE_TEXT"
    }
}
