package ua.od.upload.fileserviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ua.od.upload.Utils.Constants;
import ua.od.upload.Utils.MediaTypeUtils;
import ua.od.upload.bean.FileInfoBean;
import ua.od.upload.fileservice.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Autowired
	private ServletContext servletContext;

	@Override
	public boolean save(MultipartFile file) {

		if (file.isEmpty()) {
			return false;
		}
		if (!isFolderExists()) {
			createFolder();
		}

		byte[] bytes;
		try {
			bytes = file.getBytes();
			Path path = Paths.get(Constants.UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<FileInfoBean> getAllFiles() {
		List<FileInfoBean> fileNames = new ArrayList<>();
		if (!isFolderExists()) {
			createFolder();
		}
		try (Stream<Path> filePathStream = Files.walk(Paths.get(Constants.UPLOADED_FOLDER))) {
			filePathStream.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					FileInfoBean bean = new FileInfoBean();
					bean.setName(filePath.getFileName().toString());
					bean.setSize(filePath.toFile().length());
					fileNames.add(bean);

				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileNames;
	}

	@Override
	public Path findPathFileByName(String name) {
		if (!isFolderExists()) {
			createFolder();
		}
		File folder = new File(Constants.UPLOADED_FOLDER);
		for (File file : folder.listFiles()) {
			if (file.isFile() & file.getName().equals(name)) {
				return file.toPath();
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<ByteArrayResource> downloadFile(String name) {

		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, name);
		Path path = findPathFileByName(name);
		if (path == null) {
			return null;
		}
		ByteArrayResource byteArrayResource = null;
		byte[] fileData = null;
		try {
			fileData = Files.readAllBytes(path);
			byteArrayResource = new ByteArrayResource(fileData);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(mediaType).contentLength(fileData.length).body(byteArrayResource);
	}

	private boolean isFolderExists() {
		File file = new File(Constants.UPLOADED_FOLDER);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	private void createFolder() {
		File folder = new File(Constants.UPLOADED_FOLDER);
		folder.mkdir();
	}

}
