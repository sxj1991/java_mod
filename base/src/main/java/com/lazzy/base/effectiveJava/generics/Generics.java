package com.lazzy.base.effectiveJava.generics;

import java.util.*;

/**
 * 根据effective java 范型章节
 * 编写泛型
 * 1.为什么需要泛型：传统的object类型转换无法保证类型的安全。（使用泛型让异常放在编译阶段）
 * 2.上下界通配符作用（元素安全可控，包含方法的参数以及参数读或写）：
 *   上界通配符 <? extends parent> 用于读取集合数据，但不可写入。<T extends parent> 泛型T只能是parent的子类
 *   下界通配符<? super child>不可直接读取child类型（需要转换为object才可读取），但能写入child类型以及child类型子类。
 * 3.类型安全的异构容器：map集合存入数据时，不使用强制转换，保证类型的安全
 */
public class Generics {
    /**
     * 类型安全的异构容器工具类
     * @param <T>
     */
     static class ConfigUtil<T> {
         private final Class<T> tClass;

         private final String configName;

         public ConfigUtil(Class<T> tClass, String configName) {
             this.tClass = tClass;
             this.configName = configName;
         }

         public Class<T> gettClass() {
             return tClass;
         }

         public String getConfigName() {
             return configName;
         }

         @Override
         public boolean equals(Object o) {
             if (this == o) return true;
             if (o == null || getClass() != o.getClass()) return false;
             ConfigUtil<?> that = (ConfigUtil<?>) o;
             return Objects.equals(tClass, that.tClass) && Objects.equals(configName, that.configName);
         }

         @Override
         public int hashCode() {
             return Objects.hash(tClass, configName);
         }
     }


     static class Config{
         private final Map<ConfigUtil<?>, Object> map = new HashMap<>();

         public <T>void putElement(Class<T> type, String key, T value){
            map.put(new ConfigUtil<>(type, key),value);
         }

         public <T> T getElement(Class<T> type, String key){
             return type.cast(map.get(new ConfigUtil<>(type, key)));
         }

     }

    public static void main(String[] args) {
        Config config = new Config();
        config.putElement(String.class, "username", "admin");
        config.putElement(String.class, "password", "123");
        config.putElement(Integer.class, "port", 5001);
        // 传入class信息和key保证取出时类型的安全性
        String username = config.getElement(String.class, "username");
        Integer port = config.getElement(Integer.class, "port");
        System.out.println("msg:"+username+"@"+port);

    }

}
