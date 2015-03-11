package com.practice.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.practice.PageCache;



@WebServlet("/url/summary")
public class HTMLSummaryServlet extends HttpServlet {

	private static final long serialVersionUID = 9057630430641791366L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

	response.setContentType("application/json");  
	PrintWriter out=response.getWriter();  

	  try{	 

		Map<String, Integer> tagMap = new HashMap<>();
		
		String url = URLDecoder.decode(request.getParameter("url"), "UTF-8");
		
		Document doc = null;
		if (PageCache.cache.get(url) == null){
			doc = Jsoup.connect(url).timeout(30000).get();
			PageCache.cache.put(url, doc);
	    }else{
	    	doc = PageCache.cache.get(url);
	    }
	    
		// get the summary 
		for (Element element : doc.getAllElements()){
			if (tagMap.containsKey(element.nodeName())){
				tagMap.put(element.nodeName(), tagMap.get(element.nodeName()) + 1);
			}else{
				tagMap.put(element.nodeName(), 1);
			}
		}
		
		JSONObject jsonObj=new JSONObject();
		
		// compute the summary of tags 
		for (String key : tagMap.keySet()){
			jsonObj.put(key, tagMap.get(key));
		}
		
		out.print(jsonObj.toJSONString());
	  }catch(Exception e){
		  e.printStackTrace();
		  response.setStatus(400);
		  out.println("Please verify the request parameters");
	  }
	}  
	
}
