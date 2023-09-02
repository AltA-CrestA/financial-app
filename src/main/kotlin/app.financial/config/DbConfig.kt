package app.financial.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.SharedCacheMode
import org.flywaydb.core.Flyway
import org.hibernate.cfg.Environment
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@Configuration
class DbConfig {
    @Value("\${spring.datasource.driverClassName}")
    private lateinit var driverClassName: String

    @Value("\${spring.datasource.url}")
    private lateinit var url: String

    @Value("\${spring.datasource.username}")
    private lateinit var username: String

    @Value("\${spring.datasource.password}")
    private lateinit var password: String

    @Value("\${spring.datasource.maximumPoolSize}")
    private var maximumPoolSize: Int = 10

    /**
     * Hibernate settings
     */
    private val dialect = "org.hibernate.dialect.PostgreSQLDialect"
    private val hbm2ddlAuto = "none"
    private val packagesToScan = arrayOf("app.financial")

    /**
     * Flyway migrations
     */
    private val migrationsLocation = "classpath:db/migration"

    @Bean
    fun hikariConfig(): HikariConfig {
        val config = HikariConfig()
        config.driverClassName = driverClassName
        config.jdbcUrl = url
        config.username = username
        config.password = password
        config.maximumPoolSize = maximumPoolSize
        config.addDataSourceProperty("characterEncoding", "utf8")
        config.addDataSourceProperty("useUnicode", "true")

        return config
    }

    @Bean
    @Primary
    @DependsOn(value = ["hikariConfig"])
    fun dataSource(@Qualifier("hikariConfig") config: HikariConfig): DataSource {
        return HikariDataSource(config)
    }

    // Конфигурированиие миграций
    @Bean(initMethod = "migrate")
    @DependsOn(value = ["dataSource"])
    fun flyway(): Flyway {
        val flyway = Flyway.configure().dataSource(
            this.url,
            this.username,
            this.password
        ).locations(this.migrationsLocation).load()

        flyway.repair()
        flyway.migrate()

        return flyway
    }

    @Bean(name = ["entityManagerFactory"])
    @DependsOn(value = ["flyway"])
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val jpaProperties = Properties()
        jpaProperties[Environment.DIALECT] = dialect
        jpaProperties[Environment.HBM2DDL_AUTO] = hbm2ddlAuto
        jpaProperties[Environment.JAKARTA_SHARED_CACHE_MODE] = SharedCacheMode.ENABLE_SELECTIVE
        jpaProperties[Environment.SHOW_SQL] = true
//		jpaProperties.put("hibernate.use_sql_comments",true)
//		jpaProperties.put("hibernate.format_sql",true)
//		jpaProperties.put("hibernate.type","trace")

        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        for (pack in packagesToScan) {
            entityManagerFactoryBean.setPackagesToScan(pack)
        }

        entityManagerFactoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.setJpaProperties(jpaProperties)

        return entityManagerFactoryBean
    }
}