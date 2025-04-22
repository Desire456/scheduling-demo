package ru.tbank.schedulingdemo.jobrunr.onetime

import org.jobrunr.jobs.lambdas.IocJobLambda
import org.jobrunr.scheduling.BackgroundJob
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tbank.schedulingdemo.jobrunr.JobRunrProfile

@RestController
@JobRunrProfile
class SampleApiController {

    @PostMapping("/api/v1")
    fun schedule(
        @RequestParam clientId: String
    ) {
        BackgroundJob.enqueue(
            IocJobLambda<OneTimeJob> { it.doJob(clientId) }
        )
    }
}