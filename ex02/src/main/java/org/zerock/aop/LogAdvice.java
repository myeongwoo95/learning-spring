package org.zerock.aop;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {	
	
	@Before("execution(* org.zerock.service.SampleService.*(..))")
	public void logBefore() {
		
		log.info("================");
	}
	
	//&& args를 이용하는 설정은 간단히 파라미터를 찾아서 기록할 때에는 유용하지만 파라미터가 다른 여러 종류의 메서드에 적용하는 데에는 간단하지 않다는 단점이 있다. 
	//이에 대한 문제는 @Around와 @ProceedingJoinPoint를 이용해서 해결할 수 있다.
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		
		log.info("str1: " + str1);
		log.info("str2: " + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService.*(..))", throwing="exception")
	public void logException(Exception exception) {
		
		log.info("Exception...!!!!!");
		log.info("exception: " + exception);
	}
	
	@Around("execution(* org.zerock.service.SampleService.* (..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		
		long start = System.currentTimeMillis();
		
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		
		//invoke method
		Object result = null;
		
		try {
			//이것은 target을 실행하는 코드이다.
			result = pjp.proceed();
		} catch(Throwable e){
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME: " + (end- start));
		
		return result;
	}
}
