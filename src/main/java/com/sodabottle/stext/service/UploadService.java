package com.sodabottle.stext.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sodabottle.stext.models.Upload;
import com.sodabottle.stext.models.UploadDetail;
import com.sodabottle.stext.repos.UploadDetailsRepo;
import com.sodabottle.stext.repos.UploadRepo;

@Service
public class UploadService {

	@Autowired
	UploadRepo uploadRepo;
	
	@Autowired
	UploadDetailsRepo uploadDetailsRepo;
	
	public Upload save(Upload upload) {
		return uploadRepo.save(upload);
	}

	public List<UploadDetail> getTranscripts(Long uploadId) {
		return uploadDetailsRepo.findPageNoAndShortTextByUploadId(uploadId);
	}

	public UploadDetail getUploadDetails(Long uploadDetailId) {
		Optional<UploadDetail> uploadDetail = uploadDetailsRepo.findById(uploadDetailId);
		if(null != uploadDetail && uploadDetail.isPresent())
			return uploadDetail.get();
		return null;
	}

	public List<Upload> getUploads(List<Long> uploadIds) {
		return uploadRepo.findByIds(uploadIds);
	}

}
