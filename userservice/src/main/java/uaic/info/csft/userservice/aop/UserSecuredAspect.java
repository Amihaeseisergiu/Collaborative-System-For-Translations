package uaic.info.csft.userservice.aop;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uaic.info.csft.userservice.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
@RequiredArgsConstructor
public class UserSecuredAspect {

    @Value("${jwt.header:Authorization}")
    private String jwtHeader;

    private final JwtUtil jwtUtil;

    @Around("@annotation(uaic.info.csft.userservice.aop.UserSecured) || @within(uaic.info.csft.userservice.aop.UserSecured)")
    public Object secure(ProceedingJoinPoint joinPoint) throws Throwable
    {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;

        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        assert response != null;

        final String token = request.getHeader(jwtHeader);
        final Claims claims = jwtUtil.getClaimsFromToken(token);

        final String userId = String.valueOf(claims.get("id"));
        final Long id = (Long) joinPoint.getArgs()[0];

        if(!userId.equals(String.valueOf(id)))
        {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
            return null;
        }

        return joinPoint.proceed();
    }
}
