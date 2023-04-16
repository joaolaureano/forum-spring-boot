package com.example.forum.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class TraceableAspect {
    private enum TraceableMessages {
        EXECUTION("Controller Execution - Endpoint [%s] Verb [%s] Method [%s]"),
        FAILURE("Controller Execution - Endpoint failed");

        String message;

        TraceableMessages(String s) {
            this.message = s;
        }

        public String getMessage() {
            return message;
        }
    }

    @Around("execution(* com.example.forum.controller.*.*(..)) ")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
            ResponseEntity response;
            String message = "";
        try {
            response = (ResponseEntity) joinPoint.proceed();
            if(response.getStatusCode().is2xxSuccessful()){

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String methodName = joinPoint.getSignature().getName();
            String verbName = request.getMethod();
            String endpoint = request.getRequestURL().toString();
            String logExecutionMessage = TraceableMessages.EXECUTION.getMessage();
            message = String.format(logExecutionMessage, endpoint, verbName, methodName);
            }

        } catch (Exception ex) {
            message = TraceableMessages.FAILURE.getMessage();
            throw ex;
        }
        finally{
            log.info(message);
        }
        return response;
    }
}
