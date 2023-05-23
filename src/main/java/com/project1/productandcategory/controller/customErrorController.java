package com.project1.productandcategory.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class customErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        String requestedUrl = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

   
        String directoryViewUrl = "directory:" + requestedUrl;

     
        return "redirect:" + directoryViewUrl;
    }

    public String getErrorPath() {
        return "/error";
    }
}
