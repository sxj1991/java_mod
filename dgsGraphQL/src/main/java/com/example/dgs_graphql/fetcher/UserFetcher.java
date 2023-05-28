package com.example.dgs_graphql.fetcher;

import com.example.dgs_graphql.dto.Resp;
import com.example.dgs_graphql.model.Clazz;
import com.example.dgs_graphql.model.Hobby;
import com.example.dgs_graphql.model.User;
import com.example.dgs_graphql.model.UserInput;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@DgsComponent
@Slf4j
public class UserFetcher {
    // 模拟数据库数据
    private final List<User> users = List.of(
            new User(1,"Stranger", "qazwsx",new Clazz("一班"),List.of(new Hobby("跳高"))),
            new User(2,"Ozark", "qazwsx7",new Clazz("二班"),List.of(new Hobby("游泳"))),
            new User(3,"Crown", "123sda",new Clazz("二班"),List.of(new Hobby("跑步"))),
            new User(4,"Me", "asda123",new Clazz("二班"),List.of(new Hobby("睡觉"))),
            new User(5,"NewBlack", "123asdag",new Clazz("二班"),List.of(new Hobby("跳高")))
    );

    @DgsQuery
    public List<User> users() {
       return users;
    }

    @DgsMutation
    public Resp createUser(@InputArgument UserInput userInput){
        log.info("获取数据:{}",userInput);
        return new Resp("创建成功",200);
    }

    @DgsData(parentType = "User", field = "createHobby")
    public List<Hobby> createHobby(DgsDataFetchingEnvironment dfe){
        // DgsCustomContextBuilderWithRequest 实现build接口方法 设置上下文信息 可用于用户认证
        // DgsContext.getCustomContext(dfe) 获取 DgsCustomContextBuilderWithRequest 返回的对象信息;
        User user = dfe.getSource();
        return user.getHobbies();
    }




}
