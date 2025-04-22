package ru.tbank.schedulingdemo.quartz.recurrent

import org.quartz.Job
import org.quartz.JobExecutionContext

/**
 * Класс задания с исполняемым кодом внутри.
 */
class RecurrentJob(
    private val recurrentJobHandler: RecurrentJobHandler
) : Job {

    override fun execute(context: JobExecutionContext) {
        recurrentJobHandler.handle()
    }

    companion object {
        const val JOB_NAME = "recurrentJob"
    }
}