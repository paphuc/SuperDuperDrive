package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.web.servlet.error.ErrorController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(Model model, HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (!Objects.isNull(status)) {
            int statusCode = Integer.parseInt(status.toString());
            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("error", "403");
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error", "404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("error", "500");
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}