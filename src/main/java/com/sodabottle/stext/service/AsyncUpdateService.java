package com.sodabottle.stext.service;

import com.sodabottle.stext.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Async
@Slf4j
@Service
public class AsyncUpdateService {

    @Autowired
    private ApiKeyServiceImpl apiKeyService;

    public void updateApiKeys(final List<Long> apiKeyIds) {
        if (CollectionUtils.isEmpty(apiKeyIds)) {
            LogUtils.logMessage("NULL ApiKeys", log, LogUtils.LogState.ERROR);
        }
        apiKeyService.incrementCurrentCountByIds(apiKeyIds);
        LogUtils.logMessage("Incremented ApiKeys Successfully " + apiKeyIds, log, LogUtils.LogState.INFO);
    }
}
