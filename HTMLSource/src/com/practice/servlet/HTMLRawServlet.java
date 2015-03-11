package com.practice.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.practice.PageCache;



@WebServlet("/url/html")
public class HTMLRawServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2480126368465266765L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
	
	response.setContentType("text/html");  
	PrintWriter out=response.getWriter();  

	  try{	 
		String url = URLDecoder.decode(request.getParameter("url"), "UTF-8");
		Document doc = null;
		if (PageCache.cache.get(url) == null){
			doc = Jsoup.connect(url).timeout(30000).get();
			PageCache.cache.put(url, doc);
	    }else{
	    	doc = PageCache.cache.get(url);
	    }
		
		// selected tag
		String tag = request.getParameter("tag");
		
		if (tag != null){
			tag = URLDecoder.decode(tag,"UTF-8");
			out.print(doc.toString().replace("&","&amp;").replace("<", "&lt;").
				replace("&lt;"+tag, "<mark>&lt;"+tag+"</mark>").
				replace("&lt;/"+tag, 
				"<mark>&lt;/"+tag+"</mark>").replace("\n", "<br>"));
		}else{
			out.print(doc.toString().replace("&","&amp;").replace("<", "&lt;").replace("\n", "<br>"));
		}
	  }catch(Exception e){
		  e.printStackTrace();
		  response.setStatus(400);
		  out.println("Please verify the request parameters");
	  }
		
	
	}  
	
}
