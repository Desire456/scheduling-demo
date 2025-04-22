package ru.tbank.schedulingdemo.dbscheduler.onetime

import com.github.kagkarlsson.scheduler.SchedulerClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tbank.schedulingdemo.dbscheduler.DbSchedulerProfile
import java.time.Instant

@RestController
@RequestMapping("/api/v1")
@DbSchedulerProfile
class SampleApiController(
    private val scheduler: SchedulerClient
) {

    @PostMapping
    fun schedule(
        @RequestParam clientId: String
    ) {
        scheduler.scheduleIfNotExists(
            ONE_TIME_TASK
                .instance(clientId)
                .data(OneTimeTaskData(clientId))
                .scheduledTo(Instant.now())
        )
    }
}
