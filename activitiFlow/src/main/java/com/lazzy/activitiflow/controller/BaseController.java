package com.lazzy.activitiflow.controller;

import com.lazzy.activitiflow.flow.WorkFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class BaseController {

    @Resource
    private WorkFlow workFlow;


    @GetMapping("start")
    public String start(){
        workFlow.genTask();
        return "startActivity";
    }

    @GetMapping("complete/{name}/{id}")
    public String completely(@PathVariable String name, @PathVariable String id){
        workFlow.completeTask(name,id);
        return "completeActivity";
    }


    @GetMapping("history/{id}")
    public String history(@PathVariable String id){
        workFlow.historyActInstanceList(id);
        workFlow.historyTaskList(id);
        workFlow.processState(id);
        return "historyActivity";
    }
}