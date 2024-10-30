package com.example.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		String filePath = "C:\\Users\\Admin\\Desktop\\git commands.txt";
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

	public void fileDownload(int id, String outputPath) {
		String query = "select fileName ,fileData from files where id = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pre = con.prepareStatement(query)) {
			pre.setInt(1, id);
			ResultSet res = pre.executeQuery();

			if (res.next()) {
				String fileName = res.getString("fileName");
				InputStream inputStream = res.getBinaryStream("fileData");
				String fullPath = outputPath + java.io.File.separator + fileName;
				try (FileOutputStream fs = new FileOutputStream(fullPath)) {
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						fs.write(buffer, 0, bytesRead);
					}
					System.out.println("File downloaded successfully as " + outputPath + " \"" + fileName);

				} catch (Exception e) {
					System.err.println("Error writing file to disk.");
					e.printStackTrace();
				}
			} else {
				System.out.println("File with ID " + id + " not found in the database.");

			}

		} catch (Exception e) {

		}

	}
}
