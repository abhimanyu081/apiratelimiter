package com.abhimanyu.apiratelimiter;

import com.abhimanyu.apiratelimiter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByName(String name);
}
