package com.example.demo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements org.springframework.boot.webmvc.error.ErrorController {

    // Spring Boot redirects ALL errors here (404, 500, etc.)
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        
        // 1. Get the error status code (e.g., 404, 500)
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // 2. If it is a 404 (Page Not Found), show your JSP
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404"; // This maps to 404.jsp
            }
        }
        
        
        return "404";
    }
}