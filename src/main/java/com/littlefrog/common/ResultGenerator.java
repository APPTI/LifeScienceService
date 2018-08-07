package com.littlefrog.common;

import org.hibernate.service.spi.ServiceException;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Response genSuccessResult() {
        Response response = new Response();
        response.setCode(ResultCode.SUCCESS);
        response.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return response;
    }

    public static Response genSuccessResult(Object data) {
        Response response = new Response();
        response.setCode(ResultCode.SUCCESS);
        response.setMessage(DEFAULT_SUCCESS_MESSAGE);
        response.setData(data);
        return response;
    }

    public static Response genFailResult(String message) {
        Response response = new Response();
        response.setCode(ResultCode.FAIL);
        response.setMessage(message);
        return response;
    }
}