package com.lazzy.base.message_sensitive;

import lombok.Data;

/**
 * 用户实体类
 *
 */
@Data
public class User {
    @Sensitive(type = SensitiveTypeEnum.NAME)
    private String name;
    @Sensitive(type = SensitiveTypeEnum.ID_NUM)
    private String card;
    @Sensitive(type = SensitiveTypeEnum.PHONE_NUM)
    private String phone;
    @Sensitive(type = SensitiveTypeEnum.CUSTOMER,prefixNoMaskLen = 3, suffixNoMaskLen = 2)
    private String password;
}
