package com.abhimanyu.apiratelimiter.repo;

import com.abhimanyu.apiratelimiter.entity.TableAPI;
import com.abhimanyu.apiratelimiter.entity.TableApiAccessQuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableAPIRepository extends JpaRepository<TableAPI,Long> {

    /***
     *
     * @param methodName
     * @return
     */
    public TableAPI findByMethodName(String methodName);

    /****
     *
     *
     * @param methodName
     * @return
     */
    public TableAPI findByApiUrl(String methodName);

}
