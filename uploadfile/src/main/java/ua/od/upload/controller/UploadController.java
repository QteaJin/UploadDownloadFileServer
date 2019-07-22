package ua.od.upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ua.od.upload.bean.UploadFileResponse;
import ua.od.upload.fileservice.FileStorageService;

@RestController
public class UploadController {
	
	@Autowired
    private FileStorageService fileStorageService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST )
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		if(fileStorageService.save(file)) {
			String fileName = file.getOriginalFilename();
			long fileSize = file.getSize();
			return new UploadFileResponse(fileName, fileSize);
		}
		return new UploadFileResponse(false);
		
		
	}
}
