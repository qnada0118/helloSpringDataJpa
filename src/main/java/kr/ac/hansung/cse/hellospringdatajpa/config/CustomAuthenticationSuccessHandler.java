package kr.ac.hansung.cse.hellospringdatajpa.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/admin");
            System.out.println("✅ 로그인 성공: " + authentication.getName());
            System.out.println("▶ 권한: " + AuthorityUtils.authorityListToSet(authentication.getAuthorities()));

        } else {
            response.sendRedirect(request.getContextPath() + "/products");
            System.out.println("✅ 로그인 성공: " + authentication.getName());
            System.out.println("▶ 권한: " + AuthorityUtils.authorityListToSet(authentication.getAuthorities()));

        }

    }
}
