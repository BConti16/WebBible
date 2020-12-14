package com.conti.webbible.views.home;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

public class BibleAPI {
	private String host;
	private String verseNumParam;
	private TreeMap<String, String> queryCache;
	private String lastResultString;
	
	public BibleAPI() {
		this.host = "https://bible-api.com/";
		this.verseNumParam = "?verse_numbers=true";
		this.queryCache = new TreeMap<String, String>();
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
		
		//check if query has already been run
		if(this.queryCache.containsKey(query)) {
			//get the results from cache and exit
			this.lastResultString = this.queryCache.get(query);
			return this;
		}
		//this.queryCache.put(new String(query), "");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get(query).asJson();
			JSONObject responseObject = response.getBody().getObject();
			this.lastResultString = responseObject.getString("text");
			
			//put query and results into the cache
			this.queryCache.put(new String(query), new String(this.lastResultString));
		}catch(UnirestException e) {
			
			//If exception is caught, set last results to empty and add to query
			this.lastResultString = "";
			this.queryCache.put(new String(query), new String(this.lastResultString));
		}
		return this;
	}
	
	public BibleAPI executeQuery(String book, String chapter, String verses) {
		String query = prepareQuery(book, chapter, verses);
		
		//check if query has been run before
		if(this.queryCache.containsKey(query)) {
			//get results from cache and return
			this.lastResultString = this.queryCache.get(query);
			return this;
		}
		//this.queryCache.put(new String(query), "");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get(query).asJson();
			JSONObject responseObject = response.getBody().getObject();
			this.lastResultString = responseObject.getString("text");
			
			//add query and results to the cache
			this.queryCache.put(new String(query), new String(this.lastResultString));
		}catch(UnirestException e) {
			
			//If exception is caught, set last results to empty and add to query
			this.lastResultString = "";
			this.queryCache.put(new String(query), new String(this.lastResultString));
		}
		return this;
	}
	
	public String getResults() {
		return this.lastResultString;
	}
	
	public String getCachedQueries() {
		//Set to empty string in case no queries have been stored
		String queries = "";
		
		//Loop through the cached queries and append them to the queries string
		for(Map.Entry<String, String> e : this.queryCache.entrySet()) {
			queries += beautifyQuery(e.getKey()) + '\n';
		}
		
		return queries;
	}
	
	//Takes a raw query (contains url and specifier) and "beautifies" it by returning only the user specified parameters
	private String beautifyQuery(String uglyQuery) {
		String beautifiedQuery = uglyQuery.split("/")[3].split("?")[0].replace('+', ' ');
		
		return beautifiedQuery;
	}
	
}
