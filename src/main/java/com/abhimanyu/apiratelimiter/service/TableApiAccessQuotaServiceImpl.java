package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableAPI;
import com.abhimanyu.apiratelimiter.entity.TableApiAccessQuota;
import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.repo.TableApiAccessQuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class TableApiAccessQuotaServiceImpl implements  TableApiAccessQuotaService{

    @Autowired
    private TableApiAccessQuotaRepository tableApiAccessQuotaRepository;

    @Override
    public TableApiAccessQuota create(TableApiAccessQuota tableApiAccessQuota) {
        return tableApiAccessQuotaRepository.save(tableApiAccessQuota);
    }

    @Override
    public TableApiAccessQuota update(TableApiAccessQuota tableApiAccessQuota) {
        return tableApiAccessQuotaRepository.save(tableApiAccessQuota);
    }

    @Override
    public TableApiAccessQuota create(TableUser tableUser, TableAPI tableAPI) {
        TableApiAccessQuota newTableApiAccessQuota = new TableApiAccessQuota();
        newTableApiAccessQuota.setApiAccessCount(1);
        newTableApiAccessQuota.setTableUser(tableUser);
        newTableApiAccessQuota.setTableAPI(tableAPI);
        newTableApiAccessQuota.setLastAccessTime(LocalDateTime.now());

        return  create(newTableApiAccessQuota);
    }

    @Override
    public TableApiAccessQuota findByTableUserTableAPI(TableUser tableUser, TableAPI tableAPI) {
        return tableApiAccessQuotaRepository.findByTableUserAndTableAPI(tableUser,tableAPI);
    }

    @Override
    public void reset(TableApiAccessQuota tableApiAccessQuota) {
        tableApiAccessQuota.setLastAccessTime(LocalDateTime.now());
        tableApiAccessQuota.setApiAccessCount(1);
        tableApiAccessQuota.setResetAt(LocalDateTime.now());
        tableApiAccessQuotaRepository.save(tableApiAccessQuota);
    }


}
