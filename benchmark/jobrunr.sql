truncate jobrunr_jobs;

do $$
begin
    for i in 1..1000000 loop
with uuid_cte as (
select
	uuid_generate_v4() as random_uuid
)
    insert
	into
	public.jobrunr_jobs
(id,
	"version",
	jobasjson,
	jobsignature,
	state,
	createdat,
	updatedat,
	scheduledat,
	recurringjobid)
select
	random_uuid,
	1,
	'{"version":1,"jobSignature":"benchmark.jobrunr.OneTimeJobHandler.handle()","jobName":"benchmark.jobrunr.OneTimeJobHandler.handle()","labels":[],"jobDetails":{"className":"benchmark.jobrunr.OneTimeJobHandler","methodName":"handle","jobParameters":[],"cacheable":true},"id":"' || random_uuid || '","jobHistory":[{"@class":"org.jobrunr.jobs.states.EnqueuedState","state":"ENQUEUED","createdAt":"2025-03-20T14:49:43.456487Z"}],"metadata":{"@class":"java.util.concurrent.ConcurrentHashMap"}}',
	'benchmark.jobrunr.OneTimeJobHandler.handle()',
	'ENQUEUED',
	now(),
	now(),
	null,
	null
from uuid_cte;
end loop;
end $$;