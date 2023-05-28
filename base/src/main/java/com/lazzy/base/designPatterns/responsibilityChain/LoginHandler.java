package com.lazzy.base.designPatterns.responsibilityChain;

import cn.hutool.core.util.ObjectUtil;
import com.lazzy.base.designPatterns.responsibilityChain.vo.LoginInfo;


public class LoginHandler extends Handler {
    @Override
    public String doHandler(LoginInfo loginInfo) throws Exception {
        System.out.println("执行登录");
        if (!(loginInfo.getUserName().equalsIgnoreCase("zhangsan")
                && loginInfo.getPassword().equalsIgnoreCase("123"))) {
            return "账户密码验证不通过";
        }
        if (!ObjectUtil.isEmpty(this.next)) {
            return this.next.doHandler(loginInfo);
        }
        return "责任链执行完毕";
    }
}
