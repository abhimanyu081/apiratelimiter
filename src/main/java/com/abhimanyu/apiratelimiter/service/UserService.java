package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * Finds user by its name
     *
     * @param userName
     * @return - User if found else null
     */
    public TableUser findUserByName(String userName);

    public TableUser getLoggedInUser();
}
