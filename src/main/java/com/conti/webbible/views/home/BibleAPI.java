package com.conti.webbible.views.home;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.TreeMap;

import org.json.JSONObject;

public class BibleAPI {
	private String host;
	private String verseNumParam;
	private final String charset = "UTF-8";
	private TreeMap<String, String> queryCache;
	private Gson gson;
	private String lastResultString;
	
	public BibleAPI() {
		this.host = "https://bible-api.com/";
		this.verseNumParam = "?verse_numbers=true";
		this.queryCache = new TreeMap<String, String>();
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	private String prepareQuery(String parameters) {
		String formattedParams = parameters.replace(' ', '+');
		return this.host + formattedParams + this.verseNumParam;
	}
	
	private String prepareQuery(String book, String chapter, String verses) {
		String formattedParams;
		if(verses == null || verses.equals("")) {
			formattedParams = book + "+" + chapter;
		}else {
			formattedParams = book + "+" + chapter + ":" + verses;
		}
		
		return this.host + formattedParams + this.verseNumParam;
	}
	
	public BibleAPI executeQuery(String parameters) {
		String query = prepareQuery(parameters);
		this.queryCache.put(new String(query), "");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get(query).asJson();
			JSONObject responseObject = response.getBody().getObject();
			this.lastResultString = responseObject.getString("text");
			//this.lastResultString = response.getHeaders().get("text").get(0);
		}catch(UnirestException e) {
			this.lastResultString = "";
		}
		return this;
	}
	
	public BibleAPI executeQuery(String book, String chapter, String verses) {
		String query = prepareQuery(book, chapter, verses);
		this.queryCache.put(new String(query), "");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get(query).asJson();
			JSONObject responseObject = response.getBody().getObject();
			this.lastResultString = responseObject.getString("text");
			//this.lastResultString = response.getHeaders().get("text").get(0);
		}catch(UnirestException e) {
			this.lastResultString = "";
		}
		return this;
	}
	
	public String getResults() {
		return this.lastResultString;
	}
	
}
