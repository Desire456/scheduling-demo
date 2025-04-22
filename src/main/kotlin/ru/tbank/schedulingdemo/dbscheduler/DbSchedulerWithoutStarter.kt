package ru.tbank.schedulingdemo.dbscheduler

import com.github.kagkarlsson.scheduler.Scheduler
import com.github.kagkarlsson.scheduler.task.SchedulableInstance
import com.github.kagkarlsson.scheduler.task.Task
import com.github.kagkarlsson.scheduler.task.TaskDescriptor
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.time.Instant
import javax.sql.DataSource

fun main() {
    val dataSource = DriverManagerDataSource("jdbc:postgresql://localhost:5435/scheduling_demo", "postgres", "postgres")
    val scheduler = scheduler(
        dataSource,
        Tasks.oneTime("oneTimeTask").execute { instance, ctx ->
            println("One time task ${instance.id}")
            Thread.sleep(1000) 
        }
    )
    val schedules = mutableListOf<SchedulableInstance<Void>>()
    repeat(1000) {
        schedules.add(
            TaskDescriptor.of("oneTimeTask")
                .instance(it.toString())
                .scheduledTo(Instant.now().minusSeconds(5))
        )
    }
    scheduler.scheduleBatch(schedules as List<SchedulableInstance<*>>)
    scheduler.start()
    Thread.sleep(10000000)
    // let's go ...
}

private fun scheduler(
    datasource: DataSource,
    vararg knownTasks: Task<*>,
): Scheduler {
    return Scheduler.create(datasource, *knownTasks)
        .threads(10)
        .build()
}