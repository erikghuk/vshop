package com.elg.vshop.error.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        final String expired = (String) httpServletRequest.getAttribute("expired");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        if (expired!=null){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Login details");
        }
    }
}
