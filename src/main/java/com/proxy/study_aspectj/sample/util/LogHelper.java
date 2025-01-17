package com.proxy.study_aspectj.sample.util;

import com.proxy.study_aspectj.sample.dto.SampleDto;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * SampleService 에서 수행되는 로직 들에 대한 AOP 포인트 컷과 메소드를 정의할 클래스
 */
@Aspect
@Component
public class LogHelper {

    private static final Logger log = LoggerFactory.getLogger(LogHelper.class);

    /*
        [ @Pointcut 표현식에서 사용되는 주요 키워드 ]
        execution(): 메소드 실행 시점을 포인트컷으로 지정
        예시: execution(* com.example.service.*.*(..)) (모든 메소드)

        within(): 특정 클래스나 패키지 내에서 실행되는 메소드에 적용
        예시: within(com.example.service.*) (service 패키지 내 모든 클래스의 메소드)

        args(): 메소드의 파라미터 타입을 기준으로 포인트컷을 지정
        예시: args(java.lang.String) (String 타입 파라미터를 받는 메소드)

        @annotation(): 특정 어노테이션이 적용된 메소드에 적용
        예시: @annotation(org.springframework.transaction.annotation.Transactional) (Transactional 어노테이션이 적용된 메소드)

        this(), target(): 프록시 객체와 대상 객체를 기준으로 포인트컷을 설정
     */

    // @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)") // 타겟이 어노테이션
    @Pointcut("within(com.proxy.study_aspectj.sample.service.*)") // 타켓이 클래스
    public void getTargetClass () {} // 일종의 alias 화

    @Pointcut("execution(* com.proxy.study_aspectj.sample.service.SampleService.getPrivateInfo(..))") // 타겟이 메소드
    public void getTargetMethod () {};

    /*
    JoinPoint는 현재 실행되고 있는 메소드에 대한 정보를 제공하는 객체 : 주로 메소드 이름, 파라미터, 타겟 객체 등 다양한 정보를 얻을 수 있음
    getSignature(): 현재 실행되는 메소드의 서명(메소드 이름, 반환 타입, 파라미터 타입 등)을 반환
    getArgs(): 메소드의 인자들을 배열로 반환
    getTarget(): 메소드를 실행한 객체를 반환
    getThis(): 프록시 객체를 반환
    */
    
    @Before("getTargetClass()")
    public void before(JoinPoint joinPoint) {
        log.info("BEFORE AOP METHOD IS RUNNING");
    }

    @After("getTargetClass()")
    public void after(JoinPoint joinPoint) {
        log.info("AFTER AOP METHOD IS RUNNING");
    }

    @Around(value = "getTargetClass()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("AROUND AOP METHOD IS START");
        Object returnObject= joinPoint.proceed();
        log.info("AROUND AOP METHOD IS END");

        // 포인트컷 메소드의 반환타입이 void 이든 객체이든 상관없이
        // joinPrint.proceed() 의 결과값을 반환해주야 한다.
        return returnObject;
    }

    @AfterReturning(value = "getTargetClass()", returning = "result") // 타겟 메소드가 리턴값이 있다면 리턴값 반환 후 실행
    public void afterReturnning(List<SampleDto> result) {
        log.info("AFTER_RETURNNING AOP METHOD IS RUNNING");
        log.info("result -> {}", result);
    }

    @AfterThrowing(value = "getTargetClass()", throwing = "exception") // 타겟 메소드가 예외를 던진다면 던진 후 실행
    public void afterThrowing(Exception exception) {
        log.info("AFTER_THROWING AOP METHOD IS RUNNING");
        log.error(exception.getLocalizedMessage());
    }
    
    // 아래는 헤더의 Authorization 도 확인 가능한지 궁금해서 찍어본 것
    @Around(value = "getTargetMethod()")
    public Object authCheckAOP (ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("AUTH-CHECK-AOP START");

        // request 요청 값 가져오기 세트로 기억하자
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String authValue = request.getHeader("Authorization");

        if (authValue.equals("pass")) {
            Object returnObject = proceedingJoinPoint.proceed();
            log.info("AUTH-CHECK-AOP END");
            return returnObject;
        } else {
            log.error("AUCH-CHECK0-AOP IS DETECTING : UNVALID AUTH");
            throw new RuntimeException("AUCH-CHECK0-AOP IS DETECTING : UNVALID AUTH");
        }
    }
}
