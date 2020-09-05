package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableAPI;
import com.abhimanyu.apiratelimiter.repo.TableAPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class TableAPIServiceImpl implements  TableAPIService{

    @Autowired
    private TableAPIRepository tableAPIRepository;

    /**
     * @author Abhimanyu
     *
     * finds TableAPI by given methodName or apiUrl.
     * If such record does not exists,
     * It creates a new TableAPI record and returns.
     * Else returns Existing TableAPI
     *
     * @param methodName
     * @param apiUrl
     * @return
     */
    @Override
    public TableAPI createIfNotExistByMethodNameOrApiUrl(String methodName, String apiUrl) {
        TableAPI tableAPI=null;
        if(!StringUtils.isEmpty(methodName)){
            tableAPI = tableAPIRepository.findByMethodName(methodName);
        }else if(!StringUtils.isEmpty(apiUrl)){
            tableAPI = tableAPIRepository.findByApiUrl(apiUrl);
        }



        if(tableAPI==null){
            TableAPI newTableApi = new TableAPI();
            newTableApi.setApiUrl(apiUrl);
            newTableApi.setMethodName(methodName);
            newTableApi=tableAPIRepository.save(newTableApi);

            return newTableApi;
        }

        return  tableAPI;
    }
}
