package com.razal.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

	//setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//setup point cut declarations
	@Pointcut("execution (* com.razal.controller.*.*(..))") // for any class,any method,with any number of arguments in controller package
	private void forControllerPackage() {}
	
	@Pointcut("execution (* com.razal.service.*.*(..))") // for any class,any method,with any number of arguments in controller package
	private void forServicePackage() {}
	
	@Pointcut("execution (* com.razal.dao.*.*(..))") // for any class,any method,with any number of arguments in controller package
	private void forDAOPackage() {}
	
	//combine all pointcuts
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	//add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		//display method we are calling
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("------ >  in @Before: calling method:  " + method);
		
		//display arguments to the method
		Object[] args = joinPoint.getArgs();
		
		for (Object tempArg : args) {
			myLogger.info("------ >  argument: " + tempArg);
		}
	}
	
	//add AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		
		//displat method we are returning from
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("------ >  in @AfterReturning: from method:  " + method);
		
		//display returned data
		myLogger.info("------ >  result: " + result );
	}
	
}
