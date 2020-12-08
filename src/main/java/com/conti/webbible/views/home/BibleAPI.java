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
	private String translationParam;
	private final String charset = "UTF-8";
	private ArrayList<String> queryCache;
	
	public BibleAPI() {
		this.host = "https://bible-api.com/";
		this.translationParam = "?translation=kjv";
		this.queryCache = new ArrayList<String>();
	}
	
}
