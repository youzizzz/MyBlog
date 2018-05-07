package com.youzi.MyBlog.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CommonController {

    @ResponseBody
    @RequestMapping(value = "ueditor",headers = "Accept=application/json")
    public String ueditor(@RequestParam("action") String action, @RequestParam("noCache") String nocache, HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("application/json;charset=utf-8");
            Resource resource = new ClassPathResource("config.json");
            File file = resource.getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    
    @RequestMapping("ImgUpload")
    @ResponseBody
    public String imgUpload3(MultipartFile upfile) {
    	
    	File file=new File("E:\\images\\"+upfile.getOriginalFilename());
    	  //url的值为图片的实际访问地址 这里我用了Nginx代理，访问的路径是http://localhost/xxx.png
        String config = "{\"state\": \"SUCCESS\"," +
                "\"url\": \"" +upfile.getOriginalFilename() + "\"," +
                "\"title\": \"" + upfile.getOriginalFilename() + "\"," +
                "\"original\": \"" + upfile.getOriginalFilename()+ "\"}";
    	try {
			upfile.transferTo(file);
			 return config;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			 return "error";
		}
              
       
    }
}
