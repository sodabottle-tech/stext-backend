package com.sodabottle.stext.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sodabottle.stext.models.UploadDetail;

@Repository
public interface UploadDetailsRepo extends JpaRepository<UploadDetail, Long> {

	@Query(value = "SELECT new com.sodabottle.stext.models.UploadDetail(u.id, u.pageNo, u.shortText) FROM UploadDetail u WHERE u.upload.id = :uploadId")
	List<UploadDetail> findPageNoAndShortTextByUploadId(@Param("uploadId") Long uploadId);
	
}
