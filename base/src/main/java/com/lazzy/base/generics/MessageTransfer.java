package com.lazzy.base.generics;

public interface MessageTransfer<T> {
   T process(T t);
}
