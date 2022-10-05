package com.lazzy.security.config;


import com.lazzy.security.entity.ResponseResult;
import com.lazzy.security.gson.GsonUtil;
import com.lazzy.security.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(),
                "权限不足");
        String json = GsonUtil.GsonToString(result);
        WebUtils.renderString(response, json);
    }
}
