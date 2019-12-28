package com.sodabottle.stext.repo;

import com.sodabottle.stext.models.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRepo extends JpaRepository<Key, Long> {

    //@Query(value = "SELECT * FROM api_key k WHERE k.apiName = :apiName", nativeQuery = true)
    //Key findByIdApiName(String apiName);


    @Query(value = "update api_key set current_count = (current_count + 1) where ids IN (:ids)", nativeQuery = true)
    void updateIds(final @Param("ids") List<Long> ids);
}
