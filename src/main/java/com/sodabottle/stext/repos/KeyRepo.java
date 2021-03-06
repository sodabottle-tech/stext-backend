package com.sodabottle.stext.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sodabottle.stext.models.Key;

@Transactional
public interface KeyRepo extends JpaRepository<Key, Long> {

	@Query(value = "SELECT t1.* " + 
			"FROM api_key t1 " + 
			"INNER JOIN " + 
			"(" + 
			"    SELECT api_name, MIN(current_count) AS min_val " + 
			"    FROM api_key " + 
			"    GROUP BY api_name" + 
			") t2 " + 
			"ON t1.api_name = t2.api_name AND " + 
			"t1.current_count  = t2.min_val", nativeQuery = true)
	List<Key> findKeys();

	@Query(value = "SELECT * FROM api_key k WHERE k.api_name = :apiName ORDER BY current_count limit 1", nativeQuery = true)
	Key findByIdApiName(String apiName);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update api_key set current_count = (current_count + 1) where id IN (:ids)", nativeQuery = true)
    void updateIds(final @Param("ids") List<Long> ids);
}
