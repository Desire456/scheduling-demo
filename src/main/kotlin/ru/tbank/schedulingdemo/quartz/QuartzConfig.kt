package ru.tbank.schedulingdemo.quartz

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.MetricRegistry
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tbank.schedulingdemo.benchmark.quartz.MetricsJobListener
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

@Configuration
class QuartzConfig {

    private val metrics = MetricRegistry()

    init {
        val reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
        reporter.start(1, TimeUnit.SECONDS)
    }

    @Bean
    fun schedulerFactoryBeanCustomizer(): SchedulerFactoryBeanCustomizer = SchedulerFactoryBeanCustomizer {
        it.setTaskExecutor(Executors.newFixedThreadPool(50))
        it.setGlobalJobListeners(MetricsJobListener(metrics))
    }

    @Bean
    @QuartzDataSource
    fun quartzDataSource(): DataSource = HikariDataSource().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5435/scheduling_demo"
        username = "postgres"
        password = "postgres"
    }
}