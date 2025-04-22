package ru.tbank.schedulingdemo.dbscheduler.onetime

import com.github.kagkarlsson.scheduler.task.TaskDescriptor
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tbank.schedulingdemo.dbscheduler.DbSchedulerProfile

private val logger = KotlinLogging.logger {}

val ONE_TIME_TASK: TaskDescriptor<OneTimeTaskData> = TaskDescriptor.of("oneTimeTask", OneTimeTaskData::class.java)

@Configuration
@DbSchedulerProfile
class OneTimeTaskConfig {

    @Bean
    fun oneTimeTask(): OneTimeTask<OneTimeTaskData> =
        Tasks.oneTime(ONE_TIME_TASK)
            .execute { instance, ctx ->
                val clientId = instance.data.clientId
                logger.info { "One time task executed for clientId = $clientId" }
            }
}

data class OneTimeTaskData(val clientId: String)