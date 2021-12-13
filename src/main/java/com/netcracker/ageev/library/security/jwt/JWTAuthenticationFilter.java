package com.netcracker.ageev.library.security.jwt;

import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.security.SecutiryConstants;
import com.netcracker.ageev.library.service.ConfigUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private ConfigUserDetailsService configUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = GetJWTFromRequest(request);
            if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
                Long userid = jwtProvider.getUserIdFromToken(jwt);
                Users user = configUserDetailsService.loadUserById(userid);
                UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(
                        user,null, Collections.emptyList()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception e) {
            LOG.error("Can not set user authentication");
        }

        filterChain.doFilter(request,response);

    }

    public String GetJWTFromRequest(HttpServletRequest request){
        String header = request.getHeader(SecutiryConstants.HEADER_STRING);
        if(StringUtils.hasText(header)&&header.startsWith(SecutiryConstants.TOKEN_PREFIX)){
            return header.split(" ")[1];
        }
        return null;

    }
}
