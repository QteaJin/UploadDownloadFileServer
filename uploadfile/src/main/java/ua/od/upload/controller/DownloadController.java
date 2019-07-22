package ua.od.upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.od.upload.fileservice.FileStorageService;

@RestController
public class DownloadController {
	@Autowired
	private FileStorageService service;
	
	@RequestMapping(value = "/download", method = RequestMethod.GET )
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("name") String name){
		
		return service.downloadFile(name);
		
	}

}
