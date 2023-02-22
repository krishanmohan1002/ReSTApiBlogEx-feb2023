package com.blog.user.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.user.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	
	@Value("${project.image}")
	private String path;

	@Override
	public String uploadImage(String path, MultipartFile image) throws IOException{
		
//		get image Name
		String imageName = image.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		String fileNameWithRandomId = randomId.concat(imageName.substring(imageName.lastIndexOf(".")));
		
		
//		full path
		String pathFull = path+File.separator+imageName;
		
//		create folder if not created
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		
//		fileCopy
	
			Long copy = Files.copy(image.getInputStream(), Paths.get(pathFull));
		
		
		return fileNameWithRandomId;
	}
	
}
