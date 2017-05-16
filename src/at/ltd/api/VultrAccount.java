package at.ltd.api;

import at.ltd.api.json.JSONObject;
import at.ltd.api.json.parser.JSONParser;
import at.ltd.api.json.parser.ParseException;

public class VultrAccount {
	
	public VultrAccount(String key) {
		this.key = key;
	}

	private String key;
	
	public Integer getBalance(){
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("account/info", null);
		if(vrr.isOK()){
			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(vrr.getResponse());
				return Integer.valueOf((String) json.get("balance"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	public String getKey() {
		return key;
	}
	
	

}
