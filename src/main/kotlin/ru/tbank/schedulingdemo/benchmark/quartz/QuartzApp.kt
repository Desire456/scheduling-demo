package ru.tbank.schedulingdemo.benchmark.quartz

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.MetricRegistry
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.impl.StdSchedulerFactory
import org.quartz.impl.jdbcjobstore.JobStoreTX
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
import org.quartz.simpl.SimpleThreadPool
import java.util.*
import java.util.concurrent.TimeUnit

fun main() {
    val metrics = MetricRegistry()
    val reporter = ConsoleReporter.forRegistry(metrics)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build()
    reporter.start(1, TimeUnit.SECONDS)

    val scheduler = StdSchedulerFactory(configureQuartzProperties()).scheduler
    scheduler.listenerManager.addJobListener(MetricsJobListener(metrics))

    scheduler.start()
//    Thread.sleep(10_000)
}

fun configureQuartzProperties(): Properties {
    val props = Properties()
    props["org.quartz.scheduler.instanceName"] = "MyScheduler"
    props["org.quartz.scheduler.batchTriggerAcquisitionMaxCount"] = 50.toString()
    props["org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow"] = 100.toString()
    props["org.quartz.threadPool.class"] = SimpleThreadPool::class.java.name
    props["org.quartz.threadPool.threadCount"] = 50.toString()
    props["org.quartz.jobStore.class"] = JobStoreTX::class.java.name
    props["org.quartz.jobStore.driverDelegateClass"] = PostgreSQLDelegate::class.java.name
    props["org.quartz.jobStore.dataSource"] = "myDS"
    props["org.quartz.jobStore.tablePrefix"] = "QRTZ_"
    props["org.quartz.jobStore.misfireThreshold"] = Long.MAX_VALUE.toString()
    props["org.quartz.dataSource.myDS.driver"] = "org.postgresql.Driver"
    props["org.quartz.dataSource.myDS.URL"] = "jdbc:postgresql://localhost:5435/scheduling_demo"
    props["org.quartz.dataSource.myDS.user"] = "postgres"
    props["org.quartz.dataSource.myDS.password"] = "postgres"
    props["org.quartz.dataSource.myDS.provider"] = "hikaricp"
    return props
}


class SimpleJob : Job {
    override fun execute(ctx: JobExecutionContext) {
//        println("asd")
    }
}