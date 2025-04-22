package ru.tbank.schedulingdemo.benchmark.quartz

import com.codahale.metrics.MetricRegistry
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener

class MetricsJobListener(
    metricRegistry: MetricRegistry
) : JobListener {

    private val executions = metricRegistry.meter("executions")

    override fun getName(): String = "metricsJobListener"

    override fun jobToBeExecuted(context: JobExecutionContext?) {
        //noop
    }

    override fun jobExecutionVetoed(context: JobExecutionContext?) {
        //noop
    }

    override fun jobWasExecuted(context: JobExecutionContext, jobException: JobExecutionException?) {
        executions.mark()
    }
}