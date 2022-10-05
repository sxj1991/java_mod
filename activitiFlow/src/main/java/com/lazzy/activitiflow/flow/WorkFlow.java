package com.lazzy.activitiflow.flow;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WorkFlow {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    @Resource
    private ProcessEngine processEngine;


    /**
     * 产生工作流
     */
    public void genTask() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process");
        logger.info("流程定义id:{}", processInstance.getProcessInstanceId());
        logger.info("流程实例id:{}", processInstance.getId());
        logger.info("流程活动id:{}", processInstance.getActivityId());
    }

    /**
     * 完成流程
     */
    public void completeTask(String name,String id) {
        //查询任务流程
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process")
                .processInstanceId(id)
                .taskAssignee(name)
                .singleResult();
        logger.info("流程定义id:{}", task.getProcessInstanceId());
        logger.info("任务 id:{}", task.getId());
        logger.info("任务负责人:{}", task.getAssignee());
        logger.info("任务名称:{}", task.getName());

        //完成任务流程
        taskService.complete(task.getId());

    }

    /**
     * 历史活动查询
     */
    public void historyActInstanceList(String id){
        List<HistoricActivityInstance> list = processEngine.getHistoryService() // 历史相关Service
                .createHistoricActivityInstanceQuery() // 创建历史活动实例查询
                .processInstanceId(id) // 执行流程实例id
                .finished()
                .list();

        for(HistoricActivityInstance hai:list){
            System.out.println("活动ID:"+hai.getId());
            System.out.println("流程实例ID:"+hai.getProcessInstanceId());
            System.out.println("活动名称："+hai.getActivityName());
            System.out.println("办理人："+hai.getAssignee());
            System.out.println("开始时间："+hai.getStartTime());
            System.out.println("结束时间："+hai.getEndTime());
            System.out.println("=================================");
        }
    }

    /**
     * 历史任务查询
     */
    public void historyTaskList(String id){
        List<HistoricTaskInstance> list=processEngine.getHistoryService() // 历史相关Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .processInstanceId(id) // 用流程实例id查询
                .finished() // 查询已经完成的任务
                .list();
        for(HistoricTaskInstance hti:list){
            System.out.println("任务ID:"+hti.getId());
            System.out.println("流程实例ID:"+hti.getProcessInstanceId());
            System.out.println("任务名称："+hti.getName());
            System.out.println("办理人："+hti.getAssignee());
            System.out.println("开始时间："+hti.getStartTime());
            System.out.println("结束时间："+hti.getEndTime());
            System.out.println("=================================");
        }
    }

    /**
     * 查询流程状态（正在执行 or 已经执行结束）
     */
    public void processState(String id){
        ProcessInstance pi=processEngine.getRuntimeService() // 获取运行时Service
                .createProcessInstanceQuery() // 创建流程实例查询
                .processInstanceId(id) // 用流程实例id查询
                .singleResult();
        if(pi!=null){
            System.out.println("流程正在执行！");
        }else{
            System.out.println("流程已经执行结束！");
        }
    }


}
