package com.example.demo.interceptor;

import java.io.IOException;
import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.util.HeaderConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String method = request.getMethod();
        String path = request.getServletPath(); // ignores context path

        if("/error".equals(path)) return true;

        // Generate correlation ID for all requests
        String correlationId = request.getHeader(HeaderConstants.CORRELATION_ID);
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }
        request.setAttribute("correlationId", correlationId);

        System.out.println("PreHandle: Executed handler for: " + request.getRequestURI());
        System.out.println("HeaderInterceptor triggered: method=" + method + ", path=" + path + ", correlationId=" + correlationId);

        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws IOException{
    	System.out.println("PostHandle: Executed handler for: " + request.getRequestURI());
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			 Exception ex) throws Exception {
    	System.out.println("AfterCompletion: Executed handler for: " + request.getRequestURI());

	}
}
