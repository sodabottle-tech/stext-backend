package com.sodabottle.stext.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sodabottle.stext.models.Key;
import com.sodabottle.stext.repo.KeyRepo;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/keys")
public class KeyController {
	
	@Autowired
	KeyRepo keyRepo;
	
	@GetMapping()
	public Key getKey(@PathVariable("apiName") String apiName ) {
		if(StringUtils.isEmpty(apiName)) {
			log.info("Get Keys - apiName cannot be null");
			return null;
		}
		log.info("Get Keys for apiName: " + apiName);
		return keyRepo.findByIdApiName(apiName);
	}
	
	@GetMapping()
	public Key getKeys() {
		if(StringUtils.isEmpty(apiName)) {
			log.info("Get Keys - apiName cannot be null");
			return null;
		}
		log.info("Get Keys for apiName: " + apiName);
		return keyRepo.findByIdApiName(apiName);
	}
	
	
	@PostMapping()
	public String postKeys(@RequestBody Key key){
		keyRepo.save(key);
		log.info("API Key Created with apiName: " + key.getApiName());
		return "API key Created Successfully!";
	}
}
