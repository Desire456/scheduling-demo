package ru.tbank.schedulingdemo.quartz.recurrent

import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tbank.schedulingdemo.quartz.ChristmasHolidayCalendar
import ru.tbank.schedulingdemo.quartz.QuartzProfile

/**
 * Конфигурация одного задания.
 */
@Configuration
@QuartzProfile
class JobConfiguration {

//    @Bean
//    fun sampleJobDetail(): JobDetail = JobBuilder
//        .newJob(RecurrentJob::class.java)
//        .withIdentity(RecurrentJob.JOB_NAME)
//        .requestRecovery(true)
//        .storeDurably()
//        .build()
//
//    @Bean
//    fun sampleJobTrigger(sampleJobDetail: JobDetail): Trigger = TriggerBuilder.newTrigger()
//        .withIdentity("${RecurrentJob.JOB_NAME}-trigger")
//        .forJob(sampleJobDetail)
//        .withSchedule(
//            SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(1)
//                .repeatForever()
//        )
//        .modifiedByCalendar(ChristmasHolidayCalendar.BEAN_NAME)
//        .build()
}