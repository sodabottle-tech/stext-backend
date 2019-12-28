package com.sodabottle.stext.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sodabottle.stext.models.Key;
import com.sodabottle.stext.repos.KeyRepo;
import com.sodabottle.stext.responses.KeyResponse;
import com.sodabottle.stext.service.AsyncUpdateService;
import com.sodabottle.stext.utils.LogUtils;
import com.sodabottle.stext.utils.LogUtils.LogState;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/keys")
public class KeyController {
	
	@Autowired
	private KeyRepo keyRepo;
	
	@Autowired
	private AsyncUpdateService asyncUpdateService;
	
	@GetMapping()
	public ResponseEntity<KeyResponse> getKeys(@RequestParam(value = "apiName", required = false) String apiName) {	
		LogUtils.appender(log);
		
		if(! StringUtils.isEmpty(apiName)) {
			LogUtils.logMessage("Get Key for key: " + apiName, log, LogState.INFO);
			Key key = keyRepo.findByIdApiName(apiName);
			LogUtils.appender(log);
			return ResponseEntity.ok().body(KeyResponse.builder().keys(Collections.singletonList(Collections.singletonMap(key.getApiName(), key.getApiKey()))).build());
		}
		
		Map<String, String> allKeys = new HashMap<>();
		List<Long> ids = new ArrayList<>();
		
		List<Key> keys = keyRepo.findKeys();
		for (Key key : keys) {
			if(! allKeys.containsKey(key.getApiName())) {
				ids.add(key.getId());
				allKeys.put(key.getApiName(), key.getApiKey());
			}
		}
		
		//Map<String, String>  allKeys = keys.stream().collect(Collectors.toMap(Key::getApiName, Key::getApiKey));
		asyncUpdateService.updateApiKeys(ids);
		LogUtils.logMessage("Get Keys", log, LogState.INFO);
		LogUtils.appender(log);
		
		return ResponseEntity.ok().body(KeyResponse.builder().keys(Collections.singletonList(allKeys)).build());
	}
	
	@PostMapping()
	public ResponseEntity<String> postKeys(@RequestBody Key key){
		LogUtils.appender(log);
		LogUtils.logMessage("Creating new API Key - Request: " + key, log, LogState.INFO);
		key.setCurrentCount(0);
		key = keyRepo.save(key);
		StopWatch sw = new StopWatch();
		sw.start();
		LogUtils.logMessage("API Key created successfully with key: " + key, log, LogState.INFO);
		sw.stop();
		System.out.println(sw.getTotalTimeMillis());
		LogUtils.appender(log);
		return ResponseEntity.ok().body("API Key Created Successfully!");
	}
}
