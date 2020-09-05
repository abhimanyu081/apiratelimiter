package com.abhimanyu.apiratelimiter.repo;

import com.abhimanyu.apiratelimiter.entity.TableUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableUserRepository extends JpaRepository<TableUser,Long> {

    public TableUser findByName(String name);
}
