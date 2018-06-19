package com.myin.team25.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
    protected Log log = LogFactory.getLog(LoggerAspect.class);
    static String name = "";
    static String type = "";     

    @Around("execution(* com..team25..*Controller.*(..)) or execution(* com..team25..*Impl.*(..)) ")

    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
        type = joinPoint.getSignature().getDeclaringTypeName();
       
        System.out.println("type:"+type);
        if (type.indexOf("Controller") > -1) {
            name = "Controller  \t:  ";
        }
        else if (type.indexOf("Service") > -1) {
            name = "Service  \t:  ";
        }  
        
        log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }
}


