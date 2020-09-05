package com.abhimanyu.apiratelimiter.controller;

import com.abhimanyu.apiratelimiter.annotation.RateLimited;
import com.abhimanyu.apiratelimiter.dto.ApiResponseDto;
import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.service.UserService;
import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RateLimited(timeUnit = RateLimitTimeUnit.MINUTES, accessCountPerTimeUnit = 10)
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponseDto<TableUser> getMessage(){
        ApiResponseDto<TableUser> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setStatusCode(200);

        TableUser tableUser = userService.findUserByName("abhimanyu");
        apiResponseDto.setData(tableUser);


        return apiResponseDto;
    }

    @GetMapping
    @RequestMapping("/2")
    public ApiResponseDto<TableUser> getMessage2(){
        ApiResponseDto<TableUser> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setStatusCode(200);

        TableUser tableUser = userService.findUserByName("abhimanyu");
        apiResponseDto.setData(tableUser);


        return apiResponseDto;
    }
}
