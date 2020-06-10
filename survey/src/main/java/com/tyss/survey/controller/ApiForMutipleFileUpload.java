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
public class ApiForMutipleFileUpload {

	@Autowired
	ServletContext context;

	@PostMapping(path="/mutipleFileupload",consumes =MediaType.MULTIPART_FORM_DATA_VALUE) // //new annotation since 4.3
 	public ResponseEntity<AdminResponse> uploads(@RequestParam("files") MultipartFile[] files) {
		boolean uploaded=false;
		try {
			for(MultipartFile file:files) {
				boolean isExist = new File(context.getRealPath("/MutipleFileUpload/")).exists();
				if (isExist) {
					new File(context.getRealPath("/MutipleFileUpload/")).mkdir();// create a folder by name TestOptimize automatically 
				}
				String fileName = file.getOriginalFilename();
				String modifString = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() +"."+ FilenameUtils.getExtension(fileName);
				 
				File serverFile = new File(context.getRealPath("/MutipleFileUpload/" + File.separator + modifString));
				try {
					FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
					uploaded=true; 
				} catch (Exception e) {
					uploaded=false;
					throw new AdminException("Not a compatble file");
				}
			}
			if(uploaded==true) {
				AdminResponse response = new AdminResponse();
				response.setError(false);
				response.setData("Files is uploaded successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else {
				AdminResponse response = new AdminResponse();
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			AdminResponse response = new AdminResponse();
			response.setError(true);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
