package com.example.controller;

import com.example.model.FilesUploadAndDownload;
import com.example.repository.FileuploadAndDowloadDbConn;

public class FilesDownloadController {
	public static void main(String[] args) {

		FilesUploadAndDownload fd = new FilesUploadAndDownload();
		fd.setId(1234);
		String outputPath = "C:\\Users\\Admin\\Downloads";
		FileuploadAndDowloadDbConn fb = new FileuploadAndDowloadDbConn();
		fb.fileDownload(fd.getId(), outputPath);

	}

}
