package ru.tbank.schedulingdemo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class SchedulingDemoApplication {

    @Bean
    fun cmr() = CommandLineRunner {
        // JobRunr
//	    val jobScheduler = JobRunr.configure()
//			.useStorageProvider(InMemoryStorageProvider())
//			.useBackgroundJobServer()
//			.useDashboard()
//			.initialize()
//			.jobScheduler
//
//		jobScheduler.scheduleRecurrently("0 * * * *") {
//			println("Hello world!")
//		}

        // Quartz
//        val scheduler = StdSchedulerFactory.getDefaultScheduler().apply {
//            start()
//        }
//        scheduler.addCalendar(
//            "christmasCalendar",
//            ChristmasHolidayCalendar(),
//            false,
//            false
//        )
//
//        val trigger: Trigger = TriggerBuilder.newTrigger()
//            .withIdentity("myTrigger", "group1")
//            .withSchedule(cronSchedule("0 0 8 * * ?")) // Daily at 8 AM
//            .modifiedByCalendar("christmasCalendar")
//            .build()

        // Db-scheduler
//        val dataSource = SingleConnectionDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", false)
//        val schedulerClient = SchedulerClient.Builder.create(dataSource)
//            .build()
//        schedulerClient.schedule(Tasks.oneTime("123").execute { println("Hello world!") }.schedulableInstance("123"))
    }
}

fun main(args: Array<String>) {
    runApplication<SchedulingDemoApplication>(*args)
}
