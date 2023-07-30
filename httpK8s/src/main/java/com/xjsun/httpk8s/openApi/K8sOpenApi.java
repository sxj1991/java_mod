package com.xjsun.httpk8s.openApi;

import com.xjsun.httpk8s.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;


/**
 * 通过http api请求访问k8s
 */
@Component
public class K8sOpenApi {
    @Value("${k8s.url}")
    private String url;


    public String findK8sVersion(){
        MultiValueMap<String, String> headers = new HttpHeaders();
        MultiValueMap<String, String> params = new HttpHeaders();
        return HttpUtil.get(url+"/api/v1/namespaces/default/pods",params,headers);
    }
}
