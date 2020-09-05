package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableAPI;

public interface TableAPIService {

    /***
     *
     * @param methodName
     * @param apiUrl
     * @return
     */
    public TableAPI createIfNotExistByMethodNameOrApiUrl(String methodName, String apiUrl);

}
