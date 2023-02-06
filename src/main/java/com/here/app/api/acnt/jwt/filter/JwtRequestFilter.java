package com.here.app.api.acnt.jwt.filter;

import com.here.app.api.acnt.jwt.service.JwtUserDetailsService;
import com.here.app.api.acnt.jwt.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//프론트엔드에서 매순간 Request를 요청 할 때마다 이 필터를 먼저 거침
@Component
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("here")
    private String JWT_PREFIX;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        // JWT 토큰은 "Bearer 토큰" 형식입니다. Bearer 단어를 제거하고 가져옵니다.
        // 토큰만
        if(requestTokenHeader != null && requestTokenHeader.startsWith(JWT_PREFIX)){
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalStateException e){
                log.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.warn("JWT Token has expired");
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String" + "JWT 토큰이 전달자 문자열로 시작하지 않음");
        }
        // Once we get the token validate it.
        // 토큰을 받으면 유효성을 검사합니다.
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            // 토큰이 유효한 경우 Spring Security를 수동으로 설정하도록 구성
            // 인증
            if(jwtTokenUtil.validateToken(jwtToken,userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                // 컨텍스트에서 인증을 설정한 후 지정합니다.
                // 현재 사용자가 인증되었음을 나타냅니다. 그래서 통과
                // 스프링 보안 구성이 성공적으로 완료되었습니다.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request,response);
    }
}
