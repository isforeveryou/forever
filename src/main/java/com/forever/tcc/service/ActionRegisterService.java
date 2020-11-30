package com.forever.tcc.service;

import com.forever.tcc.api.BusinessAction;
import com.forever.tcc.entity.ThreadLocalData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author WJX
 * @date 2020/10/16 10:57
 */
@Aspect
@Component
@Lazy(false)
public class ActionRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(ActionRegisterService.class);

    /**
     * 切点
     */
    @Pointcut("@annotation(com.forms.lego.dtx.api.BusinessAction)")
    private void cutMethod() {

    }

    /**
     * 环绕
     */
    @Around("cutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result;

        try {
            // 执行源方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.info(joinPoint.getSignature().getName() + " invoke fail, cannot register tcc method");
            throw e;
        }

        // tcc事务未开始,不注册tcc方法
        if (ThreadLocalData.getAction().get() == null) {
            logger.info("Transactional not start," + joinPoint.getSignature().getName() + " cannot register tcc method");
            return result;
        }

        // register cancel,confirm method
        BusinessAction action = getDeclaredAnnotation(joinPoint);
        ThreadLocalData.setConfirmAction(action.name(), action.commitMethod());
        ThreadLocalData.setCancelAction(action.name(), action.rollbackMethod());
        ThreadLocalData.setActionClass(action.name(), joinPoint.getTarget().getClass());
        logger.info(joinPoint.getSignature().getName() + " register tcc method:" + action.commitMethod() + "," + action.rollbackMethod());

        return result;
    }

    /**
     * 获取BusinessAction注解
     */
    private BusinessAction getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        return objMethod.getDeclaredAnnotation(BusinessAction.class);
    }



}
