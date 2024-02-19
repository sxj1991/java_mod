package com.lazzy.quartz.custom_quartz.controller;

import com.lazzy.quartz.custom_quartz.common.ScheduleConstants;
import com.lazzy.quartz.custom_quartz.entity.SysJob;
import com.lazzy.quartz.custom_quartz.task.ScheduleUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

/**
 * quartz定时器控制层
 * 简单实现定时器的crud功能
 * 其他功能根据可自由定制
 */
@RestController
public class QuartzController {
    private final Scheduler scheduler;

    public QuartzController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 查询数据库 定时任务数据
     *
     * @return
     */
    @GetMapping
    public String getSchedulerJob(){
        return "success";
    }

    @PostMapping("cron")
    public String addSchedulerJob(@RequestBody SysJob job) throws SchedulerException {
        //新增成功 创建调度器
        ScheduleUtils.createScheduleJob(scheduler, job);
        return "success";
    }

    /**
     * 更新操作 会删除之前的任务 重新创建 本质就是重新创建任务
     * @param job
     * @return
     * @throws SchedulerException
     */
    @PutMapping("cron")
    public String updateSchedulerJob(@RequestBody SysJob job) throws SchedulerException {
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(job.getJobId(), job.getJobGroup());
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
        return "success";
    }

    @DeleteMapping("cron")
    public String deleteSchedulerJob(@RequestBody SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, job.getJobGroup()));
        return "success";
    }

    @PutMapping("status")
    public String updateSchedulerJob(@RequestBody String status,@RequestBody SysJob job) throws SchedulerException {
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            return resumeJob(job);
        }
        else {
            return pauseJob(job);
        }

    }

    /**
     * 恢复任务
     * @param job
     * @return
     * @throws SchedulerException
     */
    private String resumeJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        return "success";
    }

    /**
     * 暂停任务
     * @param job
     * @return
     * @throws SchedulerException
     */
    private String pauseJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));

        return "success";
    }

}
