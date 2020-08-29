package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.User;
import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * Finds user by its name
     *
     * @param userName
     * @return - User if found else null
     */
    public User findUserByName(String userName);
    public boolean checkApiAccessLimit(String userName,int apiLimitCountPerSecond) throws ApiAccessCountExhausted;

    public String getCurrentUserName();
}
