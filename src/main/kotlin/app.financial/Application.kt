package app.financial

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = [
    "app.financial",
    "org.telegram.telegrambots.starter"
])
@EnableJpaRepositories(basePackageClasses = [(Application::class)])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}