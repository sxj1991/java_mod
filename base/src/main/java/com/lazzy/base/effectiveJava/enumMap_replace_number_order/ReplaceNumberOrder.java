package com.lazzy.base.effectiveJava.enumMap_replace_number_order;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用enumMap代替序数索引
 */
public class ReplaceNumberOrder {
    @ToString
    @AllArgsConstructor
    public static class Plant {
        public enum Type {
            // 春种
            SPRING,
            //秋种
            AUTUMN,
            // 夏种
            SUMMER,
        }

        private final String name;
        private final Type type;

        public static void main(String[] args) {
            Plant[] garden = new Plant[]{
//                    new Plant("A11", Type.SPRING),
                    new Plant("B20", Type.SUMMER),
//                    new Plant("C30", Type.AUTUMN),
                    new Plant("B23", Type.SUMMER)
            };

            /*
                泛型数组会产生警告，且type.ordinal()是枚举字段本身顺序，不易读易懂。即便使用也需要进一步封装，才能较好的提供服务。
             */
            Set<Plant>[] types = (Set<Plant>[]) new Set[Type.values().length];
            for (int x = 0; x  < Type.values().length; x++) {
                types[x] = new HashSet<>();
            }
            for(Plant p : garden){
                types[p.type.ordinal()].add(p);
            }
            for (int x=0;x< types.length;x++){
                System.out.println(Type.values()[x] + ":" + types[x]);
            }
            /*
                EnumMap 整个代码更加简单易读，没用依赖枚举自身顺序（序数）
             */
            Map<Type, Set<Plant>> typeMap = new EnumMap<>(Type.class);
            for(Plant p : garden){
                // 如果循环中根据枚举字段Set<Plant> plants 会创建不同对象，多个"相同"枚举字段对象则会返回同一对象。
                Set<Plant> plants = typeMap.computeIfAbsent(p.type, type -> {
                    // 参数type 即枚举字段信息
                    HashSet<Plant> plantSet = new HashSet<>();
                    return plantSet;
                });
                plants.add(p);
            }
            typeMap.forEach( (type,set) -> System.out.println(type + ":" + set));
        }
    }

}
