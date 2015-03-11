package com.practice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.nodes.Document;

public class PageCache {

	// TODO - clean old entries (LRU policy)  
	public static final Map<String, Document>
			cache = new ConcurrentHashMap<>();

	
}
