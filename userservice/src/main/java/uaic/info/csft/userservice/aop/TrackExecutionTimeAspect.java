package uaic.info.csft.userservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Configuration
public class TrackExecutionTimeAspect {

    @Around("@annotation(uaic.info.csft.userservice.aop.TrackExecutionTime) || @within(uaic.info.csft.userservice.aop.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Instant start = Instant.now();

        try{
            return joinPoint.proceed();
        } finally {

            long end = Duration.between(start, Instant.now()).toMillis();

            System.out.printf("Method %s.%s() took %d ms to execute%n", className, methodName, end);
        }
    }
}
