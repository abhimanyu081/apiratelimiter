package com.abhimanyu.apiratelimiter.repo;

import com.abhimanyu.apiratelimiter.entity.TableAPI;
import com.abhimanyu.apiratelimiter.entity.TableApiAccessQuota;
import com.abhimanyu.apiratelimiter.entity.TableUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableApiAccessQuotaRepository extends JpaRepository<TableApiAccessQuota,Long> {

    /***
     *
     * @param tableUser
     * @param tableAPI
     * @return
     */
    public TableApiAccessQuota findByTableUserAndTableAPI(TableUser tableUser, TableAPI tableAPI);

}
