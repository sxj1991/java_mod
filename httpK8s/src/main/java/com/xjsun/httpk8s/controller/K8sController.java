package com.xjsun.httpk8s.controller;

import com.xjsun.httpk8s.openApi.K8sOpenApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class K8sController {
    private final K8sOpenApi k8sOpenApi;

    public K8sController(K8sOpenApi k8sOpenApi) {
        this.k8sOpenApi = k8sOpenApi;
    }

    @GetMapping("/version")
    public String getK8sVersion(){
        return k8sOpenApi.findK8sVersion();
    }
}
