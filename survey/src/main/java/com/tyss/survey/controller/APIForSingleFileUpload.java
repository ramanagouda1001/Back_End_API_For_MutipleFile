package com.tyss.survey.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.survey.dto.AdminResponse;
import com.tyss.survey.exception.AdminException;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class APIForSingleFileUpload {

	@Autowired
	ServletContext context;
	
	@PostMapping(path="/uploadmyfile",consumes =MediaType.MULTIPART_FORM_DATA_VALUE) // //new annotation since 4.3
    public ResponseEntity<AdminResponse> singleMyFileUpload(@RequestParam("file") MultipartFile file) {

		boolean uploaded;
		boolean isExist = new File(context.getRealPath("/TestOPtimize/")).exists();
		if (isExist) {
			new File(context.getRealPath("/TestOPtimize/")).mkdir();// create a folder by name TestOptimize automatically 
		}
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		String modifString = FilenameUtils.getBaseName(fileName)+"."+ FilenameUtils.getExtension(fileName);
		 System.out.println(modifString);
		File serverFile = new File(context.getRealPath("/TestOPtimize/" + File.separator + modifString));
		try {
			FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
			uploaded=true;
			//here we write the file in the identified folder 
		} catch (Exception e) {
			throw new AdminException("Not a compatble file");
		}
		if (uploaded==true) {
			AdminResponse response = new AdminResponse();
			response.setError(false);
			response.setData("File is uploaded successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			AdminResponse response = new AdminResponse();
			response.setError(true);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
