package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableAPI;
import com.abhimanyu.apiratelimiter.entity.TableApiAccessQuota;
import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;
import com.abhimanyu.apiratelimiter.util.RateLimitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RateLimitingServiceImpl implements RateLimitingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimitingServiceImpl.class);

    @Autowired
    private TableAPIService tableAPIService;

    @Autowired
    private TableApiAccessQuotaService tableApiAccessQuotaService;

    @Override
    public boolean isApiAccessAllowed(RateLimitTimeUnit timeUnit, int apiLimitCountPerTimeUnit, TableUser tableUser, String methodName, String apiUrl) {
        TableAPI tableAPI = tableAPIService.createIfNotExistByMethodNameOrApiUrl(methodName, apiUrl);

        if (tableAPI == null) {
            LOGGER.error("Could not create tableAPI. method = {}, api = {}", methodName, apiUrl);
        }

        TableApiAccessQuota tableApiAccessQuota = tableApiAccessQuotaService.findByTableUserTableAPI(tableUser, tableAPI);

        if (tableApiAccessQuota == null) {
            tableApiAccessQuotaService.create(tableUser, tableAPI);
            return true;
        } else {

            int timePassedSinceLastAccess = RateLimitUtil.getTimeDelta(timeUnit, tableApiAccessQuota.getLastAccessTime());
            if (timePassedSinceLastAccess <= 1) {
                if (tableApiAccessQuota.getApiAccessCount() <= apiLimitCountPerTimeUnit) {
                    tableApiAccessQuota.incrementApiAccessCount();
                    tableApiAccessQuotaService.update(tableApiAccessQuota);

                    LOGGER.info("Access Allowed. Time passed since API was last accessed is {} {}. ", timePassedSinceLastAccess, timeUnit.name());
                    return true;
                } else {
                    LOGGER.error("API count exhausted for user {}", tableUser.getName());
                    throw new ApiAccessCountExhausted("API count exhausted for user " + tableUser.getName());
                }
            } else {
                LOGGER.info("Set access counter to 1 and Allowed Access. Time passed since API was last accessed is {} {}. ", timePassedSinceLastAccess, timeUnit.name());
                tableApiAccessQuotaService.reset(tableApiAccessQuota);
                return true;
            }


        }

    }
}
