package com.myspring.pro28.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
 private static String CURRENT_IMAGE_REPO_PATH="c:\\spring\\image_repo";
 
 @RequestMapping("/form")
 public String form() {
	 return "uploadForm";
 }
 
 @RequestMapping(value="/upload", method=RequestMethod.POST)
 public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
		                                     HttpServletResponse response) throws Exception{
	 multipartRequest.setCharacterEncoding("utf-8");
	 Map map = new HashMap();
	 Enumeration enu = multipartRequest.getParameterNames();
	 while(enu.hasMoreElements()) {
		 String name=(String)enu.nextElement();
		 String value=multipartRequest.getParameter(name);
		 map.put(name, value);
	 }
	 
	 List fileList = fileProcess(multipartRequest);
	 map.put("fileList",fileList);
	 ModelAndView mav=new ModelAndView();
	 mav.addObject("map",map);
	 mav.setViewName("result");
	 return mav;
 }

private List<String> fileProcess(MultipartHttpServletRequest multipartRequest)
		                                                                                        throws Exception{
	List<String> fileList=new ArrayList<String>();
	Iterator<String> fileNames=multipartRequest.getFileNames();
	while(fileNames.hasNext()) {
		String fileName=fileNames.next();
		MultipartFile mFile=multipartRequest.getFile(fileName);
		String originalFileName=mFile.getOriginalFilename();
		fileList.add(originalFileName);
		File file=new File(CURRENT_IMAGE_REPO_PATH+"\\"+fileName);
		if(mFile.getSize()!=0) {//File null Check
			if(!file.exists()) {//寃쎈줈�긽�뿉 �뙆�씪�씠 議댁옱�븯吏��븡�쑝硫�
				if(file.getParentFile().mkdirs()) {//寃쎈줈�뿉 �빐�떦�븯�뒗 �뵒�젆�넗由� 紐⑤몢 �깮�꽦
					file.createNewFile();//�뙆�씪�깮�꽦
				}
			}
			mFile.transferTo(new File(CURRENT_IMAGE_REPO_PATH+"\\"+originalFileName));
			System.out.println(mFile.getName());
		}
		
	}
	return fileList;
}
 
 
 
}
