package com.shopping.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.common.utils.FastDFSClient;
import com.shopping.common.utils.JsonUtils;

@Controller
public class PicUploadController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody    //mime类型： application/json    text/plain
	public String uploadFile(MultipartFile uploadFile){
		 //浏览器兼容问题
		try {
			FastDFSClient  client = new FastDFSClient("classpath:conf/fast.conf");
			String filename = uploadFile.getOriginalFilename();
			//得到文件的扩展名
			String ext = filename.substring(filename.lastIndexOf(".")+1);
			//http://192.168.25.133/group1/M00/00/00/wKgZhVoOe3aALj1KAAAWa-61Ovo891.jpg
			//存储图片的服务器需要支持通过http协议访问图片 ：nginx:http服务器
			String paths = client.uploadFile(uploadFile.getBytes(), ext);
			
			String path =IMAGE_SERVER_URL+paths;
			
			Map map =new HashMap();
			map.put("error", 0);
			map.put("url", path);
			//把map转成json字符串
			return JsonUtils.objectToJson(map);
		
		} catch (Exception e) {
			e.printStackTrace();
			Map map =new HashMap();
			map.put("error", 1);
			map.put("message", "上传失败");
			//把map转成json字符串
			return JsonUtils.objectToJson(map);
		}
		
	}
}
