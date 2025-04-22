package ru.tbank.schedulingdemo.dbscheduler.recurrent

import com.github.kagkarlsson.scheduler.task.helper.RecurringTask
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import com.github.kagkarlsson.scheduler.task.schedule.Schedules
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tbank.schedulingdemo.dbscheduler.DbSchedulerProfile

@Configuration
@DbSchedulerProfile
class RecurrentTaskConfig {

    @Bean
    fun recurrentTask(recurrentTaskHandler: RecurrentTaskHandler): RecurringTask<Void> =
        Tasks.recurring("recurringTask", Schedules.cron("*/2 * * * * *"))
            .execute(recurrentTaskHandler)
}