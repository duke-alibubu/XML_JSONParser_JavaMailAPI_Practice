package com.cz3003.pmoreport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class JSONReader {
	private static String streamToString(InputStream inputStream) {
	    String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
	    return text;
	  }
  	//Crawling data from online API
	public static JSONObject jsonGetRequest(String urlQueryString) {
	    JSONObject json = null;
	    JSONParser jsonParser = new JSONParser();
	    try {
	      URL url = new URL(urlQueryString);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setDoOutput(true);
	      connection.setInstanceFollowRedirects(false);
	      connection.setRequestMethod("GET");
	      connection.setRequestProperty("Content-Type", "application/json");
	      connection.setRequestProperty("charset", "utf-8");
	      connection.connect();
	      InputStream inStream = connection.getInputStream();
	      /*json = streamToString(inStream); */
	      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
	      Object obj = jsonParser.parse(reader);
	      json = (JSONObject) obj;
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    } catch (ParseException e) {
           e.printStackTrace();
        }
	    return json;
	  }
	public String process(String urlQueryString) {
		JSONObject json = jsonGetRequest(urlQueryString);
		String ans = "";
		JSONArray jsonArray = (JSONArray)json.get("items");
		JSONObject obj = (JSONObject)jsonArray.get(0);
		JSONObject list = (JSONObject)obj.get("readings");
		ans += "  O3 sub index: " + getDetails((JSONObject)list.get("o3_sub_index")) + "\n \n ";		
		ans += "  PM10 twenty four hourly: " + getDetails((JSONObject)list.get("pm10_twenty_four_hourly")) + "\n \n";
		ans += "  PM10 sub index: " + getDetails((JSONObject)list.get("pm10_sub_index")) + "\n \n";
		ans += "  CO sub index: " + getDetails((JSONObject)list.get("co_sub_index")) + "\n \n";
		ans += "  PM25: " + getDetails((JSONObject)list.get("pm25_twenty_four_hourly")) + "\n \n";
		ans += "  CO in maximum eight hour: " + getDetails((JSONObject)list.get("co_eight_hour_max")) + "\n \n";
		ans += "  NO2 in max one hour: " + getDetails((JSONObject)list.get("no2_one_hour_max")) + "\n \n";
		ans += "  SO2 twenty four hourly: " + getDetails((JSONObject)list.get("so2_twenty_four_hourly")) + "\n \n";
		ans += "  PM25 sub index: " + getDetails((JSONObject)list.get("pm25_sub_index")) + "\n \n";
		ans += "  PSI twenty four hourly: " + getDetails((JSONObject)list.get("psi_twenty_four_hourly")) + "\n \n";
		ans += "  O3 in maximum eight hour: " + getDetails((JSONObject)list.get("o3_eight_hour_max")) + "\n \n";
		
		return ans;
	}
	public static String getDetails(JSONObject json) {
		String ans ="";
		ans += "EAST:" + json.get("east") + " - " +  "CENTRAL:" + json.get("central") + " - " + "SOUTH:" + json.get("south") + " - " + "NORTH:" + json.get("north") + " - " + "WEST:" + json.get("west") + " - " + "NATIONAL:" + json.get("national") ; 
		return ans;
	}
}
