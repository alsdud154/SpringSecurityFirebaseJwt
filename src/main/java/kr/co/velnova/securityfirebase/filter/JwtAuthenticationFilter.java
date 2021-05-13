package kr.co.velnova.securityfirebase.filter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import kr.co.velnova.securityfirebase.entity.ErrorCode;
import kr.co.velnova.securityfirebase.entity.ErrorResponse;
import kr.co.velnova.securityfirebase.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옵니다.
        String jwt = CommonUtil.resolveToken((HttpServletRequest) request);

        String type = ((HttpServletRequest) request).getHeader("type");
        String instanceName = null;

        if ("user".equals(type)) {
            instanceName = "userFirebaseApp";
        } else {
            instanceName = "adminFirebaseApp";
        }

        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance(FirebaseApp.getInstance(instanceName)).verifyIdToken(jwt);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();

            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.FORBIDDEM);

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.getWriter().write(CommonUtil.convertObjectToJson(errorResponse));

            return;
        }

        Map<String, Object> claims = decodedToken.getClaims();

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(() -> "ROLE_USER");
        grantedAuthorities.add(() -> "ROLE_ADMIN");
        grantedAuthorities.add(() -> "ROLE_SUPER");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims, "", grantedAuthorities));

        chain.doFilter(request, response);
    }

}