package ru.tbank.schedulingdemo.benchmark.dbscheduler

import com.codahale.metrics.MetricRegistry
import com.github.kagkarlsson.scheduler.stats.StatsRegistry
import com.github.kagkarlsson.scheduler.stats.StatsRegistry.CandidateStatsEvent
import com.github.kagkarlsson.scheduler.stats.StatsRegistry.ExecutionStatsEvent
import com.github.kagkarlsson.scheduler.stats.StatsRegistry.SchedulerStatsEvent
import com.github.kagkarlsson.scheduler.task.ExecutionComplete


class BenchmarkStatsRegistry(metrics: MetricRegistry) : StatsRegistry {
    private val executions = metrics.meter("executions")
    private val unexpectedErrors = metrics.meter("unexpected_errors")

    override fun register(e: SchedulerStatsEvent) {
        if (e == SchedulerStatsEvent.UNEXPECTED_ERROR) {
            unexpectedErrors.mark()
        }
    }

    override fun register(e: CandidateStatsEvent) {
    }

    override fun register(e: ExecutionStatsEvent) {
    }

    override fun registerSingleCompletedExecution(completeEvent: ExecutionComplete) {
        executions.mark()
    }
}