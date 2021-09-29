package com.example.project.bank.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogsAspect {
    @Pointcut("execution(* com.example.project.bank.api.RestApiExceptionHandler.handle*(*, ..))")
    private void allMethodsStartWithHandle(){
    }

    @Before("allMethodsStartWithHandle()")
    public void before(JoinPoint joinPoint) {

        Object firstParameter = joinPoint.getArgs()[0];
        if(firstParameter instanceof Throwable){
            Throwable ex = (Throwable) firstParameter;

            log.error(AopProxyUtils.ultimateTargetClass(firstParameter).getName(),ex);
            log.error(getStackMessage(ex));
        }
    }

    private String getStackMessage(Throwable throwable) {
        return throwable == null ? "" : throwable.getMessage();
    }
}