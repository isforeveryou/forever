package com.forever.tcc.api;

/**
 * @author WJX
 * @date 2020/10/16 17:03
 */
public interface ActionServiceProxy {

    /**
     * the Transaction method collection
     * @param tryAction method call back interface
     * @param <T> service parameter type
     * @return service method return type
     */
    <T> T execute(TryActionCallBack<T> tryAction);

    /**
     * start a transaction
     * @param actionName transaction name
     * @param customerNo customer number
     * @param txId transaction id
     */
    void start(String actionName, String customerNo, String txId);
}
