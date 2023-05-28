package com.lazzy.base.effectiveJava.composition_over_inheritance;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("t");
        arrayList.add("s");
        arrayList.add("o");
        AList<String> aList = new AList<>();
        aList.addAll(arrayList);
        System.out.println(aList.getCount());
//        BList<String> bList = new BList<>();
//        bList.addAll(arrayList);
//        System.out.println(bList.getCount());
    }
}
