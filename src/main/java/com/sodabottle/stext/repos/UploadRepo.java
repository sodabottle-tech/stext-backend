package com.sodabottle.stext.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sodabottle.stext.models.Upload;

@Repository
public interface UploadRepo  extends JpaRepository<Upload, Long> {

	@Query(value = "SELECT * FROM upload u WHERE u.id in (:uploadIds)", nativeQuery = true)
	List<Upload> findByIds(List<Long> uploadIds);

}
