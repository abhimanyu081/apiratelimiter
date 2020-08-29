package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.UserRepository;
import com.abhimanyu.apiratelimiter.annotation.RateLimited;
import com.abhimanyu.apiratelimiter.entity.User;
import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import com.abhimanyu.apiratelimiter.exception.UserNotFoundException;
import com.abhimanyu.apiratelimiter.util.RateLimitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public String getCurrentUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    @RateLimited(timeUnit = TimeUnit.SECONDS, accessCountPerTimeUnit = 2)
    public User findUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public boolean checkApiAccessLimit(String userName, int apiLimitCountPerSecond) throws ApiAccessCountExhausted {
        User user = findUserByName(userName);

        if (user == null) {
            throw new UserNotFoundException("User with name " + userName + " does not exist");
        }

        //if 1 second has not passed since last access time
        //then check the api access count
        //
        if (user.getLastAccessTime() == null) {
            user.resetApiAccessCount();
            user.resetLastAccessTime();
        } else if (RateLimitUtil.diffInMinutes(user.getLastAccessTime(), LocalDateTime.now()) <= 1) {
            if (user.getApiAccessCount() <= apiLimitCountPerSecond) {
                user.incrementApiAccessCount();
            } else {
                throw new ApiAccessCountExhausted("API count exhausted for user " + user.getName());
            }
        } else {
            user.resetApiAccessCount();
            user.resetLastAccessTime();
        }
        userRepository.save(user);
            return true;
    }
}
