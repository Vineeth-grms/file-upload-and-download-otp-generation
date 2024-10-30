package com.example.controller;

import com.example.model.FilesUploadAndDownload;
import com.example.repository.FileuploadAndDowloadDbConn;

public class FilesUploadController {
	public static void main(String[] args) {
		FilesUploadAndDownload fu = new FilesUploadAndDownload();
		fu.setId(111);
		fu.setFileName("data.txt");
		FileuploadAndDowloadDbConn fb = new FileuploadAndDowloadDbConn();
		int result = fb.fileUpload(fu);

		if (result > 0) {
			System.out.println("File uploaded and saved successfully in the database.");
		} else {
			System.out.println("File upload failed.");
		}

	}

}
