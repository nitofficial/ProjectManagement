/**
	 * @author Nithish	
*/
package com.example.demo.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constants.Constants;
import com.example.demo.model.FileModel;
import com.example.demo.model.FileSubDocument;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Cloudinary {
	
		
	public static FileModel uploadToCloudinary(MultipartFile file,String defect_id) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		LinkedMultiValueMap<String, String> HeaderMap = new LinkedMultiValueMap<>();
		HeaderMap.add("Content-disposition", "form-data; name=file; filename=" + file.getOriginalFilename());
		HeaderMap.add("Content-type", "multipart/form-data");
		HttpEntity<byte[]> fileInBytes = new HttpEntity<byte[]>(file.getBytes(), HeaderMap);

		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", fileInBytes);
		body.add("upload_preset", Constants.CLOUDINARY_UPLOAD_PRESET);

		HttpEntity<LinkedMultiValueMap<String, Object>> reqEntity = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(Constants.CLOUDINARY_URL, HttpMethod.POST, reqEntity,
				String.class);

		FileSubDocument filesubdocument = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.readValue(response.getBody(), FileSubDocument.class);
		FileModel filemodel = new FileModel();
		filemodel.setDefect_id(defect_id);

		List<FileSubDocument> list = new ArrayList<FileSubDocument>();
		list.add(filesubdocument);
		filemodel.setFilesubdocument(list);

		return filemodel;
		
		
		
	}
	

}
