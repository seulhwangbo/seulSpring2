package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UploadController {
	
	@RequestMapping(value = "upLoadFormStart")
	public String upLoadFormStart(Model model) {
		System.out.println("upLoadFormStart Start ... ");
		return "upLoadFormStart";
	}
	
	@RequestMapping(value = "uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		System.out.println("uploadForm Get Start");
		System.out.println();
	}

	@RequestMapping(value = "uploadForm", method= RequestMethod.POST)
	public String uploadForm(HttpServletRequest request, Model model) 
			throws IOException, Exception{
		
		Part image = request.getPart("file1");
		InputStream inputStream = image.getInputStream();
		
		String fileName = image.getSubmittedFileName();
		String [] split = fileName.split("\\.");
		String originalName = split[split.length -2];
		String suffix = split[split.length -1];
		
		System.out.println("originalName => " + originalName);
		System.out.println("fileName => " + fileName);
		System.out.println("suffix => " + suffix);
		//SERVLET 상속받지 못했을 때 가지고 오는 방법
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		
		System.out.println("uploadForm POST Start");
		String savedName = uploadFile(originalName, inputStream, uploadPath, suffix);
		//service => DB CRUD
		log.info("Return savedName: "  + savedName);
		model.addAttribute("savedName",savedName);
		
		return "uploadResult";
	}

	private String uploadFile(String originalName, InputStream inputStream, String uploadPath, String suffix)throws IOException, Exception{
		// universally unique identifier(UUID)
		UUID uid = UUID.randomUUID();
		// requestPath = requestPath + "/resources/image";
		System.out.println("uploadPath -> " + uploadPath);
		// Directory 생성
		File fielDirectory = new File(uploadPath);
		if (!fielDirectory.exists()) {
			fielDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName + "." + suffix;
		log.info("saveName: " + savedName);
		// 임시 파일 생성
		File tempFile = new File(uploadPath + savedName);
		// ---------------------------------------------------
		// 백업 파일 생성
		File tempFile3 = new File("c:/backup/" +savedName);
		FileOutputStream outputStream3 = new FileOutputStream(tempFile3);
		// ---------------------------------------------------
		
		// 생성된 임시파일에 요청으로 넘어온 file 의 inputStream 복사
		try  (FileOutputStream outputStream = new FileOutputStream(tempFile)){
				int read;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes))!=-1) {
					//Target File에 요청으로 넘어온 file의 InputStream 복사
					outputStream.write(bytes, 0, read);
					// backUp파일에 요청으로 넘어온 file의 InputStream 복사
					outputStream3.write(bytes, 0, read);
				}
			} finally {
				System.out.println("UpLoad The end;");
			}
			outputStream3.close();
		return savedName;
	}
	
	@RequestMapping(value = "uploadFileDelete", method = RequestMethod.GET)
	public String uploadFileDelete(HttpServletRequest request, Model model) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		// backUp folder
		String uploadPath3 ="c:/backup/";
		String delFile = request.getParameter("delFile");//delFile
		System.out.println("uploadFileDelete GET Start");
		String deleteFile  = uploadPath + delFile; // metadata 삭제
		String deleteFile3 = uploadPath3 + delFile; // backup 삭제
		System.out.println("uploadFileDelete deleteFile - > " + deleteFile);
		
		int delResult  = upFileDelete(deleteFile);
		int delResult3 = upFileDelete(deleteFile3);
		model.addAttribute("deleteFile", deleteFile);
		model.addAttribute("delResult",  delResult);
		
		
		return "uploadResult";
	}

	private int upFileDelete(String deleteFileName) {
		int result = 0;
		log.info("upFileDelete result > " + deleteFileName);
		File file = new File(deleteFileName);
		if (file.exists()) {
			if(file.delete()) {
				System.out.println("삭제 성공");
				result = 1;
			}
			else {
				System.out.println("삭제 실패");
				result = 0;
			}
		}
		else {
			System.out.println("삭제할 파일이 없어");
			result = -1;
		}
		return result;
	}
}
