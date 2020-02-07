package com.sodabottle.stext.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sodabottle.stext.models.Upload;
import com.sodabottle.stext.models.UploadDetail;
import com.sodabottle.stext.models.dtos.PushOverDto;
import com.sodabottle.stext.models.dtos.TelegramDto;
import com.sodabottle.stext.service.AsyncPushOverService;
import com.sodabottle.stext.service.FileStorageService;
import com.sodabottle.stext.service.PDFReaderService;
import com.sodabottle.stext.service.TelegramClient;
import com.sodabottle.stext.service.UploadService;
import com.sodabottle.stext.utils.LogUtils;
import com.sodabottle.stext.utils.LogUtils.LogState;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private PDFReaderService pdfReaderService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private AsyncPushOverService pushOverService;

    @Autowired
    private TelegramClient telegramClient;

    @PostMapping("/uploads")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
    	
    	LogUtils.appender(log);
    	if(null != file && !file.getContentType().equals("application/pdf"))
			return ResponseEntity.badRequest().body("Upsupported Type! Please upload only PDF format.");
    	
        Path filePath = fileStorageService.storeFile(file);
        String fileName = filePath.getFileName().toString();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        
        Upload upload = new Upload(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        upload = uploadService.save(upload);
        LogUtils.logMessage("File Uploaded Successfully", log, LogState.INFO);
        
        pdfReaderService.readFile(filePath, upload);
        LogUtils.logMessage("File Read and Save Successfully", log, LogState.INFO);

        //Delete File
		if(filePath.toFile().delete())
			LogUtils.logMessage("File deleted successfully", log, LogState.INFO);
        else
        	LogUtils.logMessage("Failed to delete the file", log, LogState.ERROR);
		LogUtils.appender(log);
		
		pushOverService.postMessage(PushOverDto.builder().title("****** File Uploaded And Saved ******").message(upload.toString()).build());
        telegramClient.postMessage(TelegramDto.builder().text(" ****** File Uploaded And Saved ****** \n \n : " + upload.toString()).build());
		
        return ResponseEntity.ok().body(upload);
    }
    
    @GetMapping("uploads")
    public ResponseEntity<List<Upload>> getUploads(@RequestParam("ids") List<Long> uploadIds) {
    	LogUtils.appender(log);
    	LogUtils.logMessage("Get Uploads for Ids: " + uploadIds, log, LogState.INFO);
    	List<Upload> uploads = uploadService.getUploads(uploadIds);
    	LogUtils.logMessage("Retrieved Uploads for Ids: " + uploadIds + " Successfully", log, LogState.INFO);
    	LogUtils.appender(log);
        return ResponseEntity.ok().body(uploads);
    }
    
    @GetMapping("uploads/{id}/upload-details")
    public ResponseEntity<List<UploadDetail>> getTranscripts(@PathVariable("id") Long uploadId) {
    	LogUtils.appender(log);
    	LogUtils.logMessage("Get Uploads Transcripts for Id: " + uploadId, log, LogState.INFO);
    	List<UploadDetail> uploadDetails = uploadService.getTranscripts(uploadId);
    	LogUtils.logMessage("Retrieved Upload Transcripts for Id: " + uploadId + " Successfully", log, LogState.INFO);
    	LogUtils.appender(log);
        return ResponseEntity.ok().body(uploadDetails);
    }
    
    @GetMapping("upload-details/{id}")
    public ResponseEntity<Object> getUploadDetails(@PathVariable("id") Long detailId) {
    	LogUtils.appender(log);
    	LogUtils.logMessage("Get Upload Details for Id: " + detailId, log, LogState.INFO);
    	UploadDetail uploadDetail = uploadService.getUploadDetails(detailId);
    	if(null == uploadDetail) {
    		LogUtils.logMessage("Not Upload Details Found for Id: " + detailId, log, LogState.ERROR);
    		return ResponseEntity.badRequest().body("Not Found!");
    	}
    	LogUtils.logMessage("Retrieved Upload Details for Id: " + detailId + " Successfully", log, LogState.INFO);
    	LogUtils.appender(log);
        return ResponseEntity.ok().body(uploadDetail);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
