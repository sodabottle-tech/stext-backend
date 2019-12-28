package com.sodabottle.stext.service;

import com.sodabottle.stext.repos.KeyRepo;
import com.sodabottle.stext.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApiKeyServiceImpl {

    @Autowired
    private KeyRepo keyRepo;

    public void incrementCurrentCountByIds(final List<Long> ids) {
        LogUtils.logMessage(" incrementCurrentCountByIds ids = " + ids, log, LogUtils.LogState.DEBUG);
        keyRepo.updateIds(ids);
        LogUtils.logMessage(" incrementCurrentCountByIds ids = " + ids, log, LogUtils.LogState.INFO);
    }
}
