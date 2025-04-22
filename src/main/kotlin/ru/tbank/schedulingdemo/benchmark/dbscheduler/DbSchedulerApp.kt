package ru.tbank.schedulingdemo.benchmark.dbscheduler

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.MetricRegistry
import com.github.kagkarlsson.scheduler.Scheduler
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import com.zaxxer.hikari.HikariDataSource
import java.time.Duration
import java.util.concurrent.TimeUnit

fun main() {
    val metrics = MetricRegistry()
    val reporter = ConsoleReporter.forRegistry(metrics)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build()
    reporter.start(1, TimeUnit.SECONDS)

    val ds = HikariDataSource().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5435/scheduling_demo"
        username = "postgres"
        password = "postgres"
    }

    val task1: OneTimeTask<Void> = Tasks.oneTime("task1")
        .execute { taskInstance, _ ->
        }

    val scheduler: Scheduler = Scheduler.create(ds, task1)
        .pollingInterval(Duration.ofSeconds(5))
        .pollUsingLockAndFetch(0.5, 4.0)
        .statsRegistry(BenchmarkStatsRegistry(metrics))
        .threads(50)
        .build()
    Runtime.getRuntime().addShutdownHook(Thread(scheduler::stop))

    scheduler.start()
}