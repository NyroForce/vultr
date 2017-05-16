package at.ltd.api;

import at.ltd.api.json.JSONArray;
import at.ltd.api.json.JSONObject;
import at.ltd.api.json.parser.JSONParser;
import at.ltd.api.json.parser.ParseException;

public class VultrRequestResult {
	public VultrRequestResult(String message, int code, String response) {
		this.code = code;
		this.message = message;
		this.response = response;
	}

	private String message;
	private String response;
	private int code;

	public String getMessage() {
		return message;
	}
	public int getCode() {
		return code;
	}

	public String getResponse() {
		return response;
	}

	public boolean isOK() {
		if (message.equals("OK")) {
			return true;
		} else {
			return false;
		}
	}

	public JSONObject toJsonObject() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(getResponse());
			return json;
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Error by converting: '"+getResponse()+"' to jsonobject.");
		}
		return null;
	}
	public JSONArray toJsonArray() {
		JSONParser parser = new JSONParser();
		try {
			JSONArray json = (JSONArray) parser.parse(getResponse());
			return json;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JSONObject toJsonObject(String convert) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(convert);
			return json;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
