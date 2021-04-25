package com.forever.tcc.service;

import com.forever.tcc.api.ActionServiceProxy;
import com.forever.tcc.api.TryActionCallBack;
import com.forever.tcc.entity.BusinessActionContext;
import com.forever.tcc.entity.ThreadLocalData;
import com.forever.utils.SpringUtils;
import com.forever.utils.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WJX
 * @date 2020/10/16 16:38
 */
@Service
public class ActionInvokeService implements ActionServiceProxy {

    private final static Integer RETRY = 3;

    private static final Logger logger = LoggerFactory.getLogger(ActionServiceProxy.class);

    @Override
    public <T> T execute(TryActionCallBack<T> tryAction) {
        T result;
        try {
            result = tryAction.doAction();
        } catch (Error | RuntimeException var5) {
            commitTccTransactionalCancel();
            throw var5;
        } catch (Throwable var6) {
            commitTccTransactionalCancel();
            throw new UndeclaredThrowableException(var6, "TryActionCallBack threw undeclared checked exception");
        }

        commitTccTransactionalConfirm();
        return result;
    }

    @Override
    public void start(String actionName, String customerNo, String txId) {
        Map<String, String> actionMap = new HashMap<>(3);

        actionMap.put(BusinessActionContext.TX_ID, txId);
        actionMap.put(BusinessActionContext.CUSTOMER_NO, customerNo);
        actionMap.put(BusinessActionContext.ACTION_NAME, actionName);

        ThreadLocalData.getAction().set(actionMap);
        logger.info("TccTransactional start-----> action:" + actionMap.toString());
    }


    private void commitTccTransactionalCancel() {
        Map<String, String> action = ThreadLocalData.getAction().get();
        Map<String, String> cancelActionMap = ThreadLocalData.getCancelAction().get();

        if (cancelActionMap != null) {
            for (Map.Entry<String, String> entry : cancelActionMap.entrySet()) {
                Class<?> serviceClazz = ThreadLocalData.getActionClass().get().get(entry.getKey());
                ThreadPool.getThreadPool().submit(() -> commitTccTransactional(action, serviceClazz, entry.getValue(), RETRY));
            }
        }
        ThreadLocalData.remove();
    }


    private void commitTccTransactionalConfirm() {
        Map<String, String> action = ThreadLocalData.getAction().get();
        Map<String, String> confirmActionMap = ThreadLocalData.getConfirmAction().get();

        if (confirmActionMap != null) {
            for (Map.Entry<String, String> entry : confirmActionMap.entrySet()) {
                Class<?> serviceClazz = ThreadLocalData.getActionClass().get().get(entry.getKey());
                ThreadPool.getThreadPool().submit(() -> commitTccTransactional(action, serviceClazz, entry.getValue(), RETRY));
            }
        }
        ThreadLocalData.remove();
    }


    private void commitTccTransactional(Map<String, String> action, Class<?> serviceClazz, String methodName, Integer retry) {

        ThreadLocalData.getAction().set(action);

        try {
            Object service = SpringUtils.getBean(serviceClazz);
            Object result = serviceClazz.getMethod(methodName).invoke(service);

            if (result instanceof Boolean) {
                if (!((Boolean) result)) {
                    if (retry <= 0) {
                        ThreadLocalData.remove();
                        logger.info("ActionServiceProxy.commitTccTransactional invoke [" + methodName + "] fail, retry:" + RETRY);
                        return;
                    }
                    retry --;
                    commitTccTransactional(action, serviceClazz, methodName, retry);
                    return;
                }
            } else {
                logger.info(methodName + " return is not boolean, please check method return type");
                return;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error("method:[ActionServiceProxy." + methodName + "] msg:[{}]", e.getMessage());
            if (retry <= 0) {
                ThreadLocalData.remove();
                logger.info("ActionServiceProxy.commitTccTransactional invoke [" + methodName + "] fail, retry:" + RETRY);
                return;
            }
            retry --;
            commitTccTransactional(action, serviceClazz, methodName, retry);
            return;
        }
        ThreadLocalData.remove();
        logger.info("ActionServiceProxy.commitTccTransactional invoke [" + methodName + "] success, retry:" + (RETRY - retry));
    }


}
