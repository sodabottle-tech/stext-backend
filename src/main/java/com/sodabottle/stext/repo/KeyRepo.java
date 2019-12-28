package com.sodabottle.stext.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sodabottle.stext.models.Key;

@Repository
public interface KeyRepo extends JpaRepository<Key, Long> {

	//@Query(value = "SELECT * FROM api_key k WHERE k.apiName = :apiName", nativeQuery = true)
	//Key findByIdApiName(String apiName);

}
