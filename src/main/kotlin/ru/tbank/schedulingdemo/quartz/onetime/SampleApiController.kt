package ru.tbank.schedulingdemo.quartz.onetime

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tbank.schedulingdemo.quartz.QuartzProfile
import java.time.LocalDateTime

@RestController
@QuartzProfile
class SampleApiController(
    private val schedulerFacade: SchedulerFacade
) {

    @PostMapping("/api/v1")
    fun schedule(
        @RequestParam clientId: String
    ) {
        schedulerFacade.scheduleOneTime(
            OneTimeJob::class.java,
            LocalDateTime.now(),
            mapOf("clientId" to clientId)
        )
    }
}