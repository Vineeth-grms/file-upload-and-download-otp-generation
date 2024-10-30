package com.example.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.model.FilesUploadAndDownload;

public class FileuploadAndDowloadDbConn {

	public static final String URL = "jdbc:mysql://localhost:3306/E_commerce";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "@1234";

	public void createFilesTable() {
		String query = "create table files (id int primary key,fileName varchar(255) ,fileData BLOB )";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {

			Statement stat = con.createStatement();
			stat.execute(query);
			System.out.println("Table created successfully...");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public int fileUpload(FilesUploadAndDownload fileUp) {
		String filePath = "C:\\Users\\Admin\\Desktop\\student.txt";
		String query = "insert into files (id,fileName ,fileData) values(?,?,?)";

		// Use try-with-resources to ensure resources are closed
		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				FileInputStream fis = new FileInputStream(new File(filePath));
				PreparedStatement pre = con.prepareStatement(query)) {

			// Set parameters for the PreparedStatement
			pre.setInt(1, fileUp.getId());
			pre.setString(2, fileUp.getFileName());
			pre.setBinaryStream(3, fis, fis.available());

			// Execute the update
			int result = pre.executeUpdate();
			
			return result;

		} catch (FileNotFoundException e) {
			System.err.println("File not found at path: " + filePath);
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Database connection or SQL query issue.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error reading the file.");
			e.printStackTrace();
		}

		return 0; // Return 0 if an error occurred

	}
}
