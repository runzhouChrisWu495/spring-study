package com.bin.spring.testAOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/*
名词解释:
    1.切点，指需要被AOP增强的方法
    2.通知，AOP在什么时候执行和需要执行什么工作
    3.切面，以上两者相加
    4.连接点，可以被作为切点的方法
重点:
    Spring Aop 只能用于方法上，因为原理是动态代理
 */


@Aspect     // 带有此注解和@EnableAspectJAutoProxy的类，会被视为一个POJO和**切面**
public class Audience {

    // 切点的含义分别是:execution + 返回类型 + 方法所属类 + 方法名 + 使用的参数
    @Pointcut("execution(* com.bin.spring.testAOP.Performance.perform(..))")    // 定义一个可重用的切点
    public void performance(){
    }

    @Pointcut("execution(* com.bin.spring.testAOP.Performance.rest(int)) "+ "&& args(n)")    // 定义一个带参数的切点
    public void rest(int n){

    }

//    @Before("performance()) ")  // 通知
//    public void silenceCel1Phones() {
//        System.out.println("silencing cell phones");
//    }
//
//    @Before ( "execution(* com.bin.spring.testAOP.Performance.perform(..))")
//    public void takeSeats(){
//        System.out.println( "Taking seats");
//    }
//
//    @AfterReturning( " execution(**testAOP.Performance.perform(..))")
//    public void applause() {
//        System.out.println("CLAP CLAF CLAP! !! ");
//    }
//
//    @AfterThrowing( "execution(**testAOP.Performance.perform(..)) ")
//    public void demandRefund(){
//        System.out.println ( " Demanding a refund" );
//    }

    @Around("performance()")        // 相当于@Before + @AfterReturning
    public void watchPerformance(ProceedingJoinPoint jp){
        try{
            System.out.println("silencing cell phones");
            System.out.println("Taking seats");
            jp.proceed();       // 注意，使用这个方法会开始切点方法，而不使用这个方法会一直阻塞
            System.out.println("CLAP CLAF CLAP! !! ");
        }catch (Throwable e){
            System.out.println(" Demanding a refund");
        }
    }

    @Before("rest(n)")
    public void waitRest(int n){
        System.out.println("Audience waiting performance for "+n+" seconds");
    }
}
