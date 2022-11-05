package com.lazzy.base.design_patterns.responsibilityChain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private String userName;
    private String password;
    private String permission;
}
