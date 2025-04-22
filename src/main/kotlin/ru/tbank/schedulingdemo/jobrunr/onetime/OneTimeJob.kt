package ru.tbank.schedulingdemo.jobrunr.onetime

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OneTimeJob {

    fun doJob(clientId: String) {
        logger.info { "One time job for clientId = $clientId" }
    }
}