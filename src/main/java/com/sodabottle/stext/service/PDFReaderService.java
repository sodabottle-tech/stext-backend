package com.sodabottle.stext.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sodabottle.stext.models.Upload;
import com.sodabottle.stext.models.UploadDetail;
import com.sodabottle.stext.repos.UploadDetailsRepo;

@Service
public class PDFReaderService {

	@Autowired
	UploadDetailsRepo uploadDetailsRepo;
	
	public void readFile(Path targetLocation, Upload upload) {
		
		List<UploadDetail> uploadDetails = new ArrayList<UploadDetail>();
		UploadDetail uploadDetail = null;
		
		try {
			PDDocument pdDoc = PDDocument.load(targetLocation.toFile());
			PDFTextStripper pdfStripper = new PDFTextStripper();

			for (int i = 1; i <= pdDoc.getNumberOfPages(); i++) {
				pdfStripper.setStartPage(i);
				pdfStripper.setEndPage(i);

				String parsedText = pdfStripper.getText(pdDoc);
				
				uploadDetail = new UploadDetail();
				uploadDetail.setPageNo(i);
				uploadDetail.setShortText(parsedText.length() > 10 ? parsedText.substring(0, parsedText.indexOf(10)) : parsedText);
				uploadDetail.setLongText(parsedText);
				uploadDetail.setUpload(upload);
				uploadDetails.add(uploadDetail);
			}
			
			uploadDetailsRepo.saveAll(uploadDetails);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
