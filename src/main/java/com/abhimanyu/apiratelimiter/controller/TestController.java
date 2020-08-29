package com.abhimanyu.apiratelimiter.controller;

import com.abhimanyu.apiratelimiter.annotation.RateLimited;
import com.abhimanyu.apiratelimiter.dto.ApiResponseDto;
import com.abhimanyu.apiratelimiter.entity.User;
import com.abhimanyu.apiratelimiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponseDto<User> getMessage(){
        ApiResponseDto<User> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setStatusCode(200);

        User user = userService.findUserByName("abhimanyu");
        apiResponseDto.setData(user);


        return apiResponseDto;
    }
}
