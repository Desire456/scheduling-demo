package ru.tbank.schedulingdemo.dbscheduler

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kagkarlsson.scheduler.boot.config.DbSchedulerCustomizer
import com.github.kagkarlsson.scheduler.serializer.JacksonSerializer
import com.github.kagkarlsson.scheduler.serializer.Serializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@DbSchedulerProfile
class DbSchedulerConfig {

    @Bean
    fun dbSchedulerCustomizer(objectMapper: ObjectMapper): DbSchedulerCustomizer = object : DbSchedulerCustomizer {
        override fun serializer(): Optional<Serializer> = Optional.of(JacksonSerializer(objectMapper))
    }
}