package com.forever.tcc.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WJX
 * @date 2020/10/16 17:09
 */
public class ThreadLocalData {

    private static ThreadLocal<Map<String, String>> action = new ThreadLocal<>();

    private static ThreadLocal<Map<String, String>> cancelAction = new ThreadLocal<>();

    private static ThreadLocal<Map<String, String>> confirmAction = new ThreadLocal<>();

    private static ThreadLocal<Map<String, Class<?>>> actionClass = new ThreadLocal<>();


    public static ThreadLocal<Map<String, String>> getAction() {
        return action;
    }

    public static ThreadLocal<Map<String, String>> getCancelAction() {
        return cancelAction;
    }

    public static ThreadLocal<Map<String, String>> getConfirmAction() {
        return confirmAction;
    }

    public static ThreadLocal<Map<String, Class<?>>> getActionClass() {
        return actionClass;
    }

    public static void setCancelAction(String actionName, String cancelName) {
        if (cancelAction.get() == null) {
            Map<String, String> cancelMap = new HashMap<>();
            cancelAction.set(cancelMap);
        }
        cancelAction.get().put(actionName, cancelName);
    }

    public static void setConfirmAction(String actionName, String confirmName) {
        if (confirmAction.get() == null) {
            Map<String, String> confirmMap = new HashMap<>();
            confirmAction.set(confirmMap);
        }
        confirmAction.get().put(actionName, confirmName);
    }

    public static void setActionClass(String actionName, Class<?> clazz) {
        if (actionClass.get() == null) {
            Map<String, Class<?>> clazzMap = new HashMap<>();
            actionClass.set(clazzMap);
        }
        actionClass.get().put(actionName, clazz);
    }


    public static void remove() {
        if (action.get() != null) {
            action.remove();
        }
        if (cancelAction.get() != null) {
            cancelAction.remove();
        }
        if (confirmAction.get() != null) {
            confirmAction.remove();
        }
        if (actionClass.get() != null) {
            actionClass.remove();
        }
    }


}
