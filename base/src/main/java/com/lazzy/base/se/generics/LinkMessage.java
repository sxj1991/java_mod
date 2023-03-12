package com.lazzy.base.se.generics;

public class LinkMessage implements MessageTransfer<String>{
    @Override
    public String process(String str) {
        str = "new-"+str;
        return str;
    }
}