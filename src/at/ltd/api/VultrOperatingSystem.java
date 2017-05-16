package at.ltd.api;

import java.util.HashMap;

import at.ltd.api.json.JSONObject;

public class VultrOperatingSystem {
	
	public VultrOperatingSystem(Integer id){
		this.id = id;
	}
	
	private Integer id;
	private String name;
	private String arch;
	private String family;
	private Boolean windows;
	
	
	public VultrOperatingSystem loadData() {
		VultrRequest vr = new VultrRequest(null);
		VultrRequestResult vrr = vr.sendReq("os/list", null);
		JSONObject jo = vrr.toJsonObject();
		setData((JSONObject) jo.get(id));
		return this;
	}

	public VultrOperatingSystem setData(JSONObject jo) {
		name = (String) jo.get("name");
		arch = (String) jo.get("arch");
		family =(String) jo.get("family");
		windows = Boolean.valueOf(jo.get("windows").toString());
		return this;
	}

	public static HashMap<Integer, VultrOperatingSystem> getOSs() {
		VultrRequest vr = new VultrRequest(null);
		VultrRequestResult vrr = vr.sendReq("os/list", null);
		JSONObject jo = vrr.toJsonObject();
		HashMap<Integer, VultrOperatingSystem> hm = new HashMap<>();
		for (Object ob : jo.keySet()) {
			JSONObject s = (JSONObject) jo.get(ob);
			hm.put(Integer.valueOf((String) ob), new VultrOperatingSystem(Integer.valueOf((String) ob)).setData(s));
		}
		return hm;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + ", ID: " + id + ", Name: " + name + ", Family: " + family;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getArch() {
		return arch;
	}

	public String getFamily() {
		return family;
	}

	public Boolean getWindows() {
		return windows;
	}

	
	

}
