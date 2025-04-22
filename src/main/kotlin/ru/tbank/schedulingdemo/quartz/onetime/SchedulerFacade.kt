package ru.tbank.schedulingdemo.quartz.onetime

import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Component
import ru.tbank.schedulingdemo.quartz.QuartzProfile
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
@QuartzProfile
class SchedulerFacade(
    private val scheduler: Scheduler
) {

    fun scheduleOneTime(
        jobClass: Class<out Job>,
        scheduleAt: LocalDateTime,
        jobData: Map<String, Any>? = null
    ) {
        val jobName = "${jobClass.simpleName}:${UUID.randomUUID()}"
        val jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity(jobName)
            .apply {
                jobData?.let { usingJobData(JobDataMap(jobData)) } ?: this
            }
            .build()
        val trigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("$jobName-trigger")
            .startAt(scheduleAt.toDate())
            .build()
        scheduler.scheduleJob(jobDetail, trigger)
    }

    private fun LocalDateTime.toDate() =
        Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}