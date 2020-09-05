package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.repo.TableUserRepository;
import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import com.abhimanyu.apiratelimiter.exception.UserNotFoundException;
import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;
import com.abhimanyu.apiratelimiter.util.RateLimitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    TableUserRepository tableUserRepository;

    @Override
    public TableUser getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return findUserByName(userDetails.getUsername());
    }

    @Override
    public TableUser findUserByName(String userName) {
        return tableUserRepository.findByName(userName);
    }
}
