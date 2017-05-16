package at.ltd.api;

import java.util.HashMap;

import at.ltd.api.json.JSONObject;

public class VultrRegion {

	private Integer id;
	private String name;
	private String country;
	private String continent;
	private String state;
	private Boolean ddosprotection;
	private Boolean blockstorage;
	private String regioncode;
	
	public VultrRegion(Integer id){
		this.id = id;
	}
	
	
	public VultrRegion loadData() {
		VultrRequest vr = new VultrRequest(null);
		VultrRequestResult vrr = vr.sendReq("regions/list", null);
		JSONObject jo = vrr.toJsonObject();
		setData((JSONObject) jo.get(id));
		return this;
	}

	public VultrRegion setData(JSONObject jo) {
		name = (String) jo.get("name");
		country = (String) jo.get("country");
		continent = (String) jo.get("continent");
		state = (String) jo.get("state");
		regioncode = (String) jo.get("regioncode");
		ddosprotection = Boolean.valueOf(jo.get("ddos_protection").toString());
		blockstorage = Boolean.valueOf(jo.get("block_storage").toString());
		return this;
	}

	public static HashMap<Integer, VultrRegion> getRegions() {
		VultrRequest vr = new VultrRequest(null);
		VultrRequestResult vrr = vr.sendReq("regions/list", null);
		JSONObject jo = vrr.toJsonObject();
		HashMap<Integer, VultrRegion> hm = new HashMap<>();
		for (Object ob : jo.keySet()) {
			JSONObject s = (JSONObject) jo.get(ob);
			hm.put(Integer.valueOf((String) ob), new VultrRegion(Integer.valueOf((String) ob)).setData(s));
		}
		return hm;
	}
	
	

	@Override
	public String toString() {
		return this.getClass().getName() + ", ID: " + id + ", NAME: " + name + ", COUNTRY: " + country;
	}


	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getCountry() {
		return country;
	}


	public String getContinent() {
		return continent;
	}


	public String getState() {
		return state;
	}


	public Boolean isDDoSProtected() {
		return ddosprotection;
	}


	public Boolean isBlockstorageSupported() {
		return blockstorage;
	}


	public String getRegioncode() {
		return regioncode;
	}
	
	
	
	

	
}
