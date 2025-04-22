INSERT INTO scheduled_tasks (task_name, task_instance, execution_time, picked, version, priority)
            SELECT 'task1', 'instance'||i::text, now(), false, 1, 0
            FROM generate_series(1, 1000000) s(i);