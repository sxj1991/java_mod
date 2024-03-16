package com.lazzy.base.javaSe;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Base {
    @Data
    public static class User{
        private String name;

        public User(String name){
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Random random = new Random(100);
        Random random1 = new Random(100);
        System.out.println(random1.equals(random));
        for (int i = 0; i < 5 ; i++) {
            System.out.println("random:"+random.nextInt(10));
            System.out.println("random1:"+random1.nextInt(10));
        }

    }
}
