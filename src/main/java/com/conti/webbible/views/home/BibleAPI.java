package com.conti.webbible.views.home;

import java.net.URLEncoder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class BibleAPI {
	private String host;
	private String verseNumParam;
	private final String charset = "UTF-8";
	private ArrayList<String> queryCache;
	
	public BibleAPI() {
		this.host = "https://bible-api.com/";
		this.verseNumParam = "?verse_numbers=true";
		this.queryCache = new ArrayList<String>(10);
	}
	
	private String prepareQuery(String parameters) {
		String formattedParams = parameters.replace(' ', '+');
		return this.host + formattedParams + this.verseNumParam;
	}
	
	private String prepareQuery(String book, String chapter, String verses) {
		String formattedParams;
		if(verses.equals("") || verses.equals(null)) {
			formattedParams = book + "+" + chapter;
		}else {
			formattedParams = book + "+" + chapter + ":" + verses;
		}
		
		return this.host + formattedParams + this.verseNumParam;
	}
	
}
