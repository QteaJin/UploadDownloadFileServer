package ua.od.upload.bean;

public class UploadFileResponse {
	private String fileName;
	private long fileSize;
	private boolean error;
	
	public UploadFileResponse() {}
	
	public UploadFileResponse(boolean error) {
		super();
		this.error = error;
	}
	
	public UploadFileResponse(String fileName, long fileSize) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	


}
