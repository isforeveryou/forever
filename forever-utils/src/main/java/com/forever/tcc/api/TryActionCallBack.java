package com.forever.tcc.api;

/**
 * @author WJX
 * @date 2020/10/16 14:01
 */
public interface TryActionCallBack<T> {

    /**
     * tcc call back interface
     * @return service method return type
     */
    T doAction();
}
