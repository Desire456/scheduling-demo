@file:Suppress("MemberVisibilityCanBePrivate")

package ru.tbank.schedulingdemo.quartz.onetime

import io.github.oshai.kotlinlogging.KotlinLogging
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

private val logger = KotlinLogging.logger {}

/**
 * Класс задания с исполняемым кодом внутри.
 */
class OneTimeJob : QuartzJobBean() {

    lateinit var clientId: String

    override fun executeInternal(context: JobExecutionContext) {
        logger.info { "One time job for clientId = $clientId" }
    }
}