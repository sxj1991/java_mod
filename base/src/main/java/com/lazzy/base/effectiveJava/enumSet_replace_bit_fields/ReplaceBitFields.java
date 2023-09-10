package com.lazzy.base.effectiveJava.enumSet_replace_bit_fields;

import java.util.EnumSet;

/**
 * 用EnumSet代替位域
 * 位域概念：在java中，我们经常会定义一些常量值，来表示一些状态，每个常量值代表的意思都不一样，而且通过位运算 | ，
 *         可以将不同的样式合并到一个值中表示，这叫做位域（bit field）
 */
public class ReplaceBitFields {
    /**
     * 位域对于计算机来说，计算速度快，但是对于人来说难以维护，不好理解。
     * 建议使用作用相同，更易理解的enumSet代替
     */
    public static class Text {
        private static final int STYLE_BOLD = 1;            //样式--粗体
        private static final int STYLE_ITALIC = 2;          //样式--斜体
        private static final int STYLE_UNDERLINE = 4;       //样式--下划线
        private static final int STYLE_STRIKETHROUGH = 8;   //样式--删除线

        public static final byte[] arr = {STYLE_BOLD, STYLE_ITALIC, STYLE_UNDERLINE, STYLE_STRIKETHROUGH};

        //将样式分解之后得出样式的组合情况
        public static void applyStyles(int style){
            System.out.println("style："+style);
            for (byte b : arr) {
                int value = b & style;
                if (value != 0) {
                    System.out.println("value："+value);
                }
            }
        }

        public static void main(String[] args) {
            //我们传入STYLE_BOLD|STYLE_UNDERLINE(合计：5)，会根据位计算得出1，4数字代表选择的类型
            Text.applyStyles(STYLE_BOLD|STYLE_UNDERLINE);
        }
    }


    public static class EnumSetTest {
        public enum Style {
            BOLD, ITALIC, UNDERLINE, STRIKETHROUGH;
        }

        public static void applyStyles(EnumSet<Style> styles) {
            for (Style style : styles) {
                System.out.println("style："+style);
            }
        }

        public static void main(String[] args) {
            EnumSet<Style> styles = EnumSet.of(Style.BOLD, Style.UNDERLINE);
            applyStyles(styles);
        }
    }

}
