package uaic.info.csft.gateway.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uaic.info.csft.gateway.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class SecurityAspect {

//    @Value("${jwt.header}")
//    private String jwtHeader;
//
//    private final JwtUtil jwtUtil;
//
//    @Pointcut("execution(public * org.springframework.cloud.netflix.zuul.web..*.*(..))")
//    public void pointCut(){
//
//    }
//    @CrossOrigin
//    @Around("pointCut()")
//    public Object beforeMethod(ProceedingJoinPoint pjp) throws Throwable {
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert servletRequestAttributes != null;
//
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        HttpServletResponse response = servletRequestAttributes.getResponse();
//        assert response != null;
//
//        final List<String> bypassAuth = List.of("/register", "/login");
//
//        Predicate<HttpServletRequest> isApiSecured = r -> bypassAuth.stream()
//                .noneMatch(uri -> r.getRequestURI().contains(uri));
//
//        if(isApiSecured.test(request))
//        {
//            if(request.getHeader(jwtHeader) == null)
//            {
//                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing authorization header");
//                return null;
//            }
//
//            final String token = request.getHeader(jwtHeader);
//
//            try {
//                jwtUtil.validateToken(token);
//            } catch(Exception e)
//            {
//                response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid JWT Token");
//                return null;
//            }
//        }
//
//        return pjp.proceed();
//    }
}
