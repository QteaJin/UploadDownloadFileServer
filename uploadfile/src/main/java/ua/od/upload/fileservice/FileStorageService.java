package ua.od.upload.fileservice;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ua.od.upload.bean.FileInfoBean;

public interface FileStorageService {

	boolean save(MultipartFile file);
	List<FileInfoBean> getAllFiles();
	Path findPathFileByName(String name);
	ResponseEntity<ByteArrayResource> downloadFile(String name);

}
