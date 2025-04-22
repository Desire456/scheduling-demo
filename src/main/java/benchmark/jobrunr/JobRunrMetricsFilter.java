package benchmark.jobrunr;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.filters.JobServerFilter;

public class JobRunrMetricsFilter implements JobServerFilter {

    private final Meter executions;

    public JobRunrMetricsFilter(MetricRegistry metrics) {
        executions = metrics.meter("executions");
    }

    public void onProcessingSucceeded(Job job) {
        executions.mark();
    }
}
