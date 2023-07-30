package com.xjsun.httpk8s.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xjsun.httpk8s.openApi.K8sOpenApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class K8sController {
    private final K8sOpenApi k8sOpenApi;

    public K8sController(K8sOpenApi k8sOpenApi) {
        this.k8sOpenApi = k8sOpenApi;
    }

    @GetMapping("/pod")
    public Map getK8sVersion(){
        String k8sVersion = k8sOpenApi.findK8sVersion();
        Gson gson = new Gson();
        return gson.fromJson(k8sVersion, HashMap.class);
    }
}
