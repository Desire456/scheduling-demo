package ru.tbank.schedulingdemo.jobrunr.recurrent

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jobrunr.jobs.annotations.Job
import org.jobrunr.jobs.annotations.Recurring
import org.springframework.stereotype.Component
import ru.tbank.schedulingdemo.jobrunr.JobRunrProfile

private val logger = KotlinLogging.logger {}

@Component
@JobRunrProfile
class RecurrentJob {

    @Recurring(cron = "*/5 * * * * *")
    @Job
    fun doRecurringJob() {
        logger.info { "Hello from JobRunr's recurrent job!" }
    }
}