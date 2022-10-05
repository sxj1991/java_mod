package com.lazzy.security.fifter;


import com.lazzy.security.cache.LocalCache;
import com.lazzy.security.entity.LoginUser;
import com.lazzy.security.exception.BaseException;
import com.lazzy.security.token.GenToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userid;
        try {
            userid = GenToken.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("token非法");
        }
        //从redis中获取用户信息
        String cacheKey = "token_" + userid;
        Object key = LocalCache.getKey(cacheKey);
        LoginUser loginUser = new LoginUser();
        if(key instanceof  LoginUser ){
             loginUser = (LoginUser) key;
        }

        if (Objects.isNull(loginUser)) {
            throw new BaseException("用户未登录");
        }
        //存入SecurityContextHolder 用户信息 密码 权限
        AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         //放行
        filterChain.doFilter(request, response);
    }
}
