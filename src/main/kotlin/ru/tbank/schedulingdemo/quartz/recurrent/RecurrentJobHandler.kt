package ru.tbank.schedulingdemo.quartz.recurrent

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import ru.tbank.schedulingdemo.quartz.QuartzProfile

private val logger = KotlinLogging.logger {}

@Component
@QuartzProfile
class RecurrentJobHandler {

    fun handle() {
        logger.info { "Hello from Quartz's recurrent job!" }
    }
}