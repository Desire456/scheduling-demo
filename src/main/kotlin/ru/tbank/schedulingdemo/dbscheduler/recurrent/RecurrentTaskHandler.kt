package ru.tbank.schedulingdemo.dbscheduler.recurrent

import com.github.kagkarlsson.scheduler.task.ExecutionContext
import com.github.kagkarlsson.scheduler.task.TaskInstance
import com.github.kagkarlsson.scheduler.task.VoidExecutionHandler
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import ru.tbank.schedulingdemo.dbscheduler.DbSchedulerProfile

private val logger = KotlinLogging.logger {}

@Component
@DbSchedulerProfile
class RecurrentTaskHandler : VoidExecutionHandler<Void> {

    override fun execute(taskInstance: TaskInstance<Void>, executionContext: ExecutionContext) {
        logger.info { "Hello from Db-scheduler's recurrent job!" }
    }
}