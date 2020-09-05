package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableAPI;
import com.abhimanyu.apiratelimiter.entity.TableApiAccessQuota;
import com.abhimanyu.apiratelimiter.entity.TableUser;

public interface TableApiAccessQuotaService {

    /***
     *
     * @param tableApiAccessQuota
     * @return
     */
    public TableApiAccessQuota create(TableApiAccessQuota tableApiAccessQuota);

    public TableApiAccessQuota update(TableApiAccessQuota tableApiAccessQuota);

    /***
     *
     * @param tableUser
     * @param tableAPI
     * @return
     */
    public TableApiAccessQuota create(TableUser tableUser, TableAPI tableAPI);

    public TableApiAccessQuota findByTableUserTableAPI(TableUser tableUser, TableAPI tableAPI);

    public void reset(TableApiAccessQuota tableApiAccessQuota);
}
