package com.example.controller;

import com.example.repository.FileuploadAndDowloadDbConn;

public class FilesCreateTableController {
	public static void main(String[] args) {
		FileuploadAndDowloadDbConn fu = new FileuploadAndDowloadDbConn();
		fu.createFilesTable();
	}

}
