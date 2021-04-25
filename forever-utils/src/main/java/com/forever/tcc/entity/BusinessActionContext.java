package com.forever.tcc.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WJX
 * @date 2020/10/15 16:56
 */
public class BusinessActionContext implements Serializable {

    private static final long serialVersionUID = 6539226288677737991L;

    public static final String TX_ID = "txId";
    public static final String CUSTOMER_NO = "custNo";
    public static final String ACTION_NAME = "actionName";
    private static final String SHARDING_KEY = "shardingKey";

    private String txId;
    private String actionId;
    private String actionName;
    private Map<String, Object> actionContext;


    public BusinessActionContext() {
        if (ThreadLocalData.getAction().get() == null) {
            Map<String, String> actionMap = new HashMap<>(3);

            actionMap.put(ACTION_NAME, "Loan_Default_Transaction");
            actionMap.put(TX_ID, Thread.currentThread().getName() + System.currentTimeMillis());

            ThreadLocalData.getAction().set(actionMap);
        }

        this.txId = ThreadLocalData.getAction().get().get(TX_ID);
        this.actionId = ThreadLocalData.getAction().get().get(TX_ID);
        this.actionName = ThreadLocalData.getAction().get().get(ACTION_NAME);

        this.actionContext = new HashMap<>();
        actionContext.put(SHARDING_KEY, ThreadLocalData.getAction().get().get(CUSTOMER_NO));
    }


//    public static void start(String actionName, String custNo, String txId) {
//        Map<String, String> actionMap = new HashMap<>(3);
//
//        actionMap.put(TX_ID, txId);
//        actionMap.put(CUSTOMER_NO, custNo);
//        actionMap.put(ACTION_NAME, actionName);
//
//        ThreadLocalData.getAction().set(actionMap);
//    }

    public Object getActionContext(String key) {
        return this.actionContext.get(key);
    }

    public String getTxId() {
        return this.txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Map<String, Object> getActionContext() {
        return this.actionContext;
    }

    public void setActionContext(Map<String, Object> actionContext) {
        this.actionContext = actionContext;
    }


    @Override
    public String toString() {
        return "BusinessActionContext{" +
                "txId='" + txId + '\'' +
                ", actionId='" + actionId + '\'' +
                ", actionName='" + actionName + '\'' +
                ", actionContext=" + actionContext +
                '}';
    }
}
