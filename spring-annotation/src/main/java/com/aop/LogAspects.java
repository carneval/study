package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspects {

    /**
     * 抽取切入点表达式
     * 1、本类引用 @xxx("pointCut()")
     * 2、其他的切面引用 @xxx("com.aop.LogAspects.pointCut()")
     */
    @Pointcut("execution(public int com.aop.MathCalculator.*(..))")
    public void pointCut(){};

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() +
          "运行...参数列表是：{" + Arrays.asList(args) + "}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "运行结束");
    }

    /**
     * @param joinPoint 一定要出现在参数列表的第一位，否则Spring无法识别
     * @param result
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() +
          "正常返回...运行结果：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println(joinPoint.getSignature().getName() +
          "运行异常...异常信息：{" + exception + "}");
    }


}
