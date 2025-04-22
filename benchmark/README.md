# Jobrunr

## SQL
`\d jobrunr_jobs;`

Table "public.jobrunr_jobs"
Column     |            Type             | Collation | Nullable | Default
----------------+-----------------------------+-----------+----------+---------
id             | character(36)               |           | not null |
version        | integer                     |           | not null |
jobasjson      | text                        |           | not null |
jobsignature   | character varying(512)      |           | not null |
state          | character varying(36)       |           | not null |
createdat      | timestamp without time zone |           | not null |
updatedat      | timestamp without time zone |           | not null |
scheduledat    | timestamp without time zone |           |          |
recurringjobid | character varying(128)      |           |          |
Indexes:
"jobrunr_jobs_pkey" PRIMARY KEY, btree (id)
"jobrunr_job_created_at_idx" btree (createdat)
"jobrunr_job_rci_idx" btree (recurringjobid)
"jobrunr_job_scheduled_at_idx" btree (scheduledat)
"jobrunr_job_signature_idx" btree (jobsignature)
"jobrunr_jobs_state_updated_idx" btree (state, updatedat)
"jobrunr_state_idx" btree (state)

```sql
SELECT
  jobAsJson
FROM
  jobrunr_jobs
WHERE
  state = $1
ORDER BY
  updatedAt ASC
LIMIT
  $2
FOR UPDATE
  SKIP LOCKED;

UPDATE jobrunr_jobs
SET
  version = $1,
  jobAsJson = $2,
  state = $3,
  updatedAt = $4,
  scheduledAt = $5
WHERE
  id = $6
  AND version = $7;
```

## Results

-- Meters ----------------------------------------------------------------------
executions
count = 296051
mean rate = 4009.77 events/second
1-minute rate = 3578.58 events/second
5-minute rate = 2859.91 events/second
15-minute rate = 2665.35 events/second

# Db-scheduler
`\d scheduled_tasks;`

Table "public.scheduled_tasks"
Column        |           Type           | Collation | Nullable | Default
----------------------+--------------------------+-----------+----------+---------
task_name            | text                     |           | not null |
task_instance        | text                     |           | not null |
task_data            | bytea                    |           |          |
execution_time       | timestamp with time zone |           | not null |
picked               | boolean                  |           | not null |
picked_by            | text                     |           |          |
last_success         | timestamp with time zone |           |          |
last_failure         | timestamp with time zone |           |          |
consecutive_failures | integer                  |           |          |
last_heartbeat       | timestamp with time zone |           |          |
version              | bigint                   |           | not null |
priority             | smallint                 |           |          |
Indexes:
"scheduled_tasks_pkey" PRIMARY KEY, btree (task_name, task_instance)
"execution_time_idx" btree (execution_time)
"last_heartbeat_idx" btree (last_heartbeat)
"priority_execution_time_idx" btree (priority DESC, execution_time)

```sql
WITH
  locked_executions AS (
    UPDATE scheduled_tasks st1
    SET
      picked = $1,
      picked_by = $2,
      last_heartbeat = $3,
      version = version + 1
    WHERE
      (st1.task_name, st1.task_instance) IN (
        SELECT
          st2.task_name,
          st2.task_instance
        FROM
          scheduled_tasks st2
        WHERE
          picked = $4
          AND execution_time <= $5
        ORDER BY
          execution_time ASC
        FOR UPDATE
          SKIP LOCKED
        LIMIT
          200
      )
    RETURNING
      st1.*
  )
SELECT
  *
FROM
  locked_executions
ORDER BY
  execution_time ASC;

DELETE FROM scheduled_tasks
WHERE
  task_name = $1
  AND task_instance = $2
  AND version = $3;
```


## Results

-- Meters ----------------------------------------------------------------------
executions
count = 396679
mean rate = 8799.77 events/second
1-minute rate = 8379.41 events/second
5-minute rate = 7988.89 events/second
15-minute rate = 7902.32 events/second

# Quartz

## Results

-- Meters ----------------------------------------------------------------------
executions
count = 2360
mean rate = 26.72 events/second
1-minute rate = 26.12 events/second
5-minute rate = 24.52 events/second
15-minute rate = 24.06 events/second

```sql
SELECT * FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
SELECT * FROM QRTZ_SIMPLE_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
SELECT * FROM QRTZ_JOB_DETAILS WHERE SCHED_NAME = 'MyScheduler' AND JOB_NAME = $1 AND JOB_GROUP = $2;
UPDATE QRTZ_TRIGGERS SET TRIGGER_STATE = $1 WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $2 AND TRIGGER_GROUP = $3 AND TRIGGER_STATE = $4;
INSERT INTO QRTZ_FIRED_TRIGGERS (SCHED_NAME, ENTRY_ID, TRIGGER_NAME, TRIGGER_GROUP, INSTANCE_NAME, FIRED_TIME, SCHED_TIME, STATE, JOB_NAME, JOB_GROUP, IS_NONCONCURRENT, REQUESTS_RECOVERY, PRIORITY) VALUES('MyScheduler', $1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12);

BEGIN;
    SELECT * FROM QRTZ_LOCKS WHERE SCHED_NAME = 'MyScheduler' AND LOCK_NAME = $1 FOR UPDATE;
    SELECT TRIGGER_STATE FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
    SELECT * FROM QRTZ_JOB_DETAILS WHERE SCHED_NAME = 'MyScheduler' AND JOB_NAME = $1 AND JOB_GROUP = $2;
    UPDATE QRTZ_FIRED_TRIGGERS SET INSTANCE_NAME = $1, FIRED_TIME = $2, SCHED_TIME = $3, STATE = $4, JOB_NAME = $5, JOB_GROUP = $6, IS_NONCONCURRENT = $7, REQUESTS_RECOVERY = $8 WHERE SCHED_NAME = 'MyScheduler' AND ENTRY_ID = $9;
    SELECT TRIGGER_NAME FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
    UPDATE QRTZ_TRIGGERS SET JOB_NAME = $1, JOB_GROUP = $2, DESCRIPTION = $3, NEXT_FIRE_TIME = $4, PREV_FIRE_TIME = $5, TRIGGER_STATE = $6, TRIGGER_TYPE = $7, START_TIME = $8, END_TIME = $9, CALENDAR_NAME = $10, MISFIRE_INSTR = $11, PRIORITY = $12 WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $13 AND TRIGGER_GROUP = $14;
    UPDATE QRTZ_SIMPLE_TRIGGERS SET REPEAT_COUNT = $1, REPEAT_INTERVAL = $2, TIMES_TRIGGERED = $3 WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $4 AND TRIGGER_GROUP = $5;
COMMIT;

BEGIN;
    SELECT * FROM QRTZ_LOCKS WHERE SCHED_NAME = 'MyScheduler' AND LOCK_NAME = $1 FOR UPDATE;
    SELECT TRIGGER_STATE, NEXT_FIRE_TIME, JOB_NAME, JOB_GROUP FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
    SELECT J.JOB_NAME, J.JOB_GROUP, J.IS_DURABLE, J.JOB_CLASS_NAME, J.REQUESTS_RECOVERY FROM QRTZ_TRIGGERS T, QRTZ_JOB_DETAILS J WHERE T.SCHED_NAME = 'MyScheduler' AND J.SCHED_NAME = 'MyScheduler' AND T.TRIGGER_NAME = $1 AND T.TRIGGER_GROUP = $2 AND T.JOB_NAME = J.JOB_NAME AND T.JOB_GROUP = J.JOB_GROUP;
    DELETE FROM QRTZ_SIMPLE_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
    DELETE FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND TRIGGER_NAME = $1 AND TRIGGER_GROUP = $2;
    SELECT COUNT(TRIGGER_NAME) FROM QRTZ_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND JOB_NAME = $1 AND JOB_GROUP = $2;
    DELETE FROM QRTZ_JOB_DETAILS WHERE SCHED_NAME = 'MyScheduler' AND JOB_NAME = $1 AND JOB_GROUP = $2;
    DELETE FROM QRTZ_FIRED_TRIGGERS WHERE SCHED_NAME = 'MyScheduler' AND ENTRY_ID = $1;
COMMIT;
```

https://innovation.ebayinc.com/stories/performance-tuning-on-quartz-scheduler/

## pg_stats

```sql
SELECT query, calls, total_exec_time, mean_exec_time
FROM pg_stat_statements where query NOT ILIKE '%insert%' AND query NOT ILIKE '%create%' AND query NOT ILIKE '%pg_stat%' AND query NOT ILIKE '%pg_catalog%' AND query NOT ILIKE '%ALTER%' and query NOT ILIKE '%DROP%'
ORDER BY mean_exec_time DESC
LIMIT 10;
```
                                                                                                                                                                                                                    query                                                                                                                                                                                                                     | calls  |   total_exec_time   |    mean_exec_time
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--------+---------------------+----------------------
update jobrunr_jobs SET version = $1, jobAsJson = $2, state = $3, updatedAt =$4, scheduledAt = $5 WHERE id = $6 and version = $7                                                                                                                                                                                                                                                                                                             | 243405 |   12032.95418799973 |  0.04943593676382991
delete from scheduled_tasks where task_name = $1 and task_instance = $2 and version = $3                                                                                                                                                                                                                                                                                                                                                     | 290969 |   4892.825186999885 | 0.016815623612824914
WITH locked_executions as (UPDATE scheduled_tasks st1 SET picked = $1, picked_by = $2, last_heartbeat = $3, version = version + $6  WHERE (st1.task_name, st1.task_instance) IN (SELECT st2.task_name, st2.task_instance FROM scheduled_tasks st2  WHERE picked = $4 and execution_time <= $5  ORDER BY execution_time ASC  FOR UPDATE SKIP LOCKED  LIMIT $7) RETURNING st1.*)  SELECT * FROM locked_executions  ORDER BY execution_time ASC |   2911 |   4856.598401999997 |   1.6683608388869813
select jobAsJson from jobrunr_jobs where state = $1 ORDER BY updatedAt ASC LIMIT $2 FOR UPDATE SKIP LOCKED                                                                                                                                                                                                                                                                                                                                   |   2182 |   476.6305530000001 |  0.21843746700275007
select jobAsJson from jobrunr_jobs where state = $1 AND updatedAt <= $2 ORDER BY updatedAt ASC LIMIT $3                                                                                                                                                                                                                                                                                                                                      |     16 |  129.54679299999998 |         8.0966745625
select * from scheduled_tasks where picked = $1 and last_heartbeat <= $2  order by last_heartbeat asc                                                                                                                                                                                                                                                                                                                                        |      1 |           48.084333 |            48.084333
SET extra_float_dqigits = 2                                                                                                                                                                                                                                                                                                                                                                                                                   |     30 |  0.5874180000000001 | 0.019580600000000004
update jobrunr_backgroundjobservers SET lastHeartbeat = $1, systemFreeMemory = $2, systemCpuLoad = $3, processFreeMemory = $4, processAllocatedMemory = $5, processCpuLoad = $6 where id = $7                                                                                                                                                                                                                                                |      6 | 0.32012700000000005 | 0.053354500000000006
select jobAsJson from jobrunr_jobs where state = $3 and scheduledAt <= $1 ORDER BY updatedAt ASC LIMIT $2                                                                                                                                                                                                                                                                                                                                    |      8 |            0.208916 |            0.0261145
SET application_name = 'PostgreSQL JDBC Driver'                                                                                                                                                                                                                                                                                                                                                                                              |     34 |            0.177082 | 0.0052082941176470                                                                                                                                                                                                                                                                                                                                |      8 |            0.208916 |            0.0261145