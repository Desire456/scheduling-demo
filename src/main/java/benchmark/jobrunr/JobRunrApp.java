package benchmark.jobrunr;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariDataSource;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.server.BackgroundJobServerConfiguration;
import org.jobrunr.storage.sql.postgres.PostgresStorageProvider;

import java.util.concurrent.TimeUnit;

public class JobRunrApp {
    public static void main(String[] args) {
        var metrics = new MetricRegistry();
        var reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        var ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5435/scheduling_demo");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        var storageProvider = new PostgresStorageProvider(ds);

        JobRunr.configure()
                .useStorageProvider(storageProvider)
                .withJobFilter(new JobRunrMetricsFilter(metrics))
                .useBackgroundJobServer(
                        BackgroundJobServerConfiguration.usingStandardBackgroundJobServerConfiguration()
                                .andWorkerCount(50)
                                .andPollIntervalInSeconds(5)
                )
                .initialize();
    }
}
