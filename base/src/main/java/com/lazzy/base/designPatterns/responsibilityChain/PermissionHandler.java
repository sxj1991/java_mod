package com.lazzy.base.designPatterns.responsibilityChain;

import cn.hutool.core.util.ObjectUtil;
import com.lazzy.base.designPatterns.responsibilityChain.vo.LoginInfo;


public class PermissionHandler extends Handler{
    @Override
    public String doHandler(LoginInfo loginInfo) throws Exception {
        System.out.println("执行权限验证");
        if(!loginInfo.getPermission().equalsIgnoreCase("admin")){
            return "权限验证不通过";
        }
        if(!ObjectUtil.isEmpty(this.next)){
            return this.next.doHandler(loginInfo);
        }
        return "责任链执行完毕";
    }
}
