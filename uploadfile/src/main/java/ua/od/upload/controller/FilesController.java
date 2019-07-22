package ua.od.upload.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.od.upload.bean.FileInfoBean;
import ua.od.upload.fileservice.FileStorageService;

@RestController
public class FilesController {

	@Autowired
	private FileStorageService service;
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public List<FileInfoBean> getAllFiles() throws IOException{
		
		return service.getAllFiles();
	}
}
