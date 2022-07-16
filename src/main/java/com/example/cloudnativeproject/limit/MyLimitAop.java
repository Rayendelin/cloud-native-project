package com.example.cloudnativeproject.limit;

import com.example.cloudnativeproject.controller.Response;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class MyLimitAop {

    /**
     * 为不同的接口实现不同的流量控制
     */
    private final Map<String, RateLimiter> limiterMap = new HashMap<>();

    /**
     * 定义切点，供around使用
     */
    @Pointcut("@annotation(MyLimit)")
    public void RequestLimit(){}


    @Around("RequestLimit()")
    public synchronized Object requestLimit(ProceedingJoinPoint joinPoint) throws Throwable{
        //获取连接点的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取注解
        MyLimit limit = method.getAnnotation(MyLimit.class);
        if(limit != null){
            //获取key，不同的key对应不同的接口
            String key = limit.key();
            RateLimiter rateLimiter = null;
            //首先查看缓存时候有对应的key
            if(!limiterMap.containsKey(key)){
                //没有缓存，创建令牌桶
                rateLimiter = RateLimiter.create(limit.permitPerSecond());
                limiterMap.put(key,rateLimiter);
                System.out.printf("新建了令牌桶%s,容量为%s\n",key,limit.permitPerSecond());
            }
            rateLimiter = limiterMap.get(key);
            //获取令牌
            boolean acquire = rateLimiter.tryAcquire(limit.time(),limit.timeunit());
            if(!acquire){
                //获取不到令牌，返回报错信息
                System.out.println(key + "获取令牌失败");
                throw new LimitException();
            }

        }
        return joinPoint.proceed();

    }

}
