package com.lazzy.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class IndexController {


    //网站首页
    @RequestMapping("/index")
    public String echart(ModelMap modelMap) {
        modelMap.addAttribute("test","niubi");
        return "/index";
    }


    //推送数据接口
    @ResponseBody
    @GetMapping("/socket/push/{sname}/{message}")
    public String pushToWeb(@PathVariable String sname,@PathVariable String message) {
        try {
            WebSocketSet.sendInfo(sname,message);
        } catch (IOException e) {
            e.printStackTrace();
            return "推送失败";
        }
        return "推送成功"+sname;
    }




}