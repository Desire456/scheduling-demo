TRUNCATE qrtz_job_details CASCADE;
TRUNCATE qrtz_simple_triggers CASCADE;
TRUNCATE qrtz_triggers cascade;

DO $$
BEGIN
    FOR i IN 1..1000000 LOOP
    INSERT INTO
    qrtz_job_details (
        sched_name,
        job_name,
        job_group,
        description,
        job_class_name,
        is_durable,
        is_nonconcurrent,
        is_update_data,
        requests_recovery,
        job_data
      )
    VALUES
      (
        'MyScheduler',
        'SimpleJob:' || i,
        'DEFAULT',
        NULL,
        'ru.tbank.schedulingdemo.benchmark.quartz.SimpleJob',
        FALSE,
        FALSE,
        FALSE,
        FALSE,
        decode(
          'ACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800',
          'hex'
        )
      );

	    INSERT INTO
      public.qrtz_triggers (
        sched_name,
        trigger_name,
        trigger_group,
        job_name,
        job_group,
        description,
        next_fire_time,
        prev_fire_time,
        priority,
        trigger_state,
        trigger_type,
        start_time,
        end_time,
        calendar_name,
        misfire_instr,
        job_data
      )
    VALUES
      (
        'MyScheduler',
        'SimpleJob:trigger:' || i,
        'DEFAULT',
        'SimpleJob:' || i,
        'DEFAULT',
        NULL,
        EXTRACT(EPOCH FROM NOW())::BIGINT,
        -1,
        5,
        'WAITING',
        'SIMPLE',
        EXTRACT(EPOCH FROM NOW())::BIGINT,
        0,
        NULL,
        0,
        decode('', 'hex')
      );

    INSERT INTO
      public.qrtz_simple_triggers (
        sched_name,
        trigger_name,
        trigger_group,
        repeat_count,
        repeat_interval,
        times_triggered
      )
    VALUES
      (
        'MyScheduler',
        'SimpleJob:trigger:' || i,
        'DEFAULT',
        0,
        0,
        0
      );
END LOOP;
END $$;