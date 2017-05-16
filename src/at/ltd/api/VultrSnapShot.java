package at.ltd.api;

import java.util.HashMap;

import at.ltd.api.json.JSONObject;
import at.ltd.server.VultrServer;

public class VultrSnapShot {

	public VultrSnapShot(String id, VultrAccount va) {
		this.id = id;
		this.va = va;
	}

	private VultrAccount va;
	private String id;
	private String description;
	private String datecreated;
	private String status;
	private Long size;
	public VultrSnapShot loadData() {
		VultrRequest vr = new VultrRequest(va.getKey());
		VultrRequestResult vrr = vr.sendReq("snapshot/list", null);
		JSONObject jo = vrr.toJsonObject();
		setData((JSONObject) jo.get(id));
		return this;
	}

	public VultrSnapShot setData(JSONObject jo) {
		description = (String) jo.get("description");
		datecreated = (String) jo.get("date_created");
		status = (String) jo.get("status");
		size = Long.valueOf(jo.get("size").toString());
		return this;
	}

	public static HashMap<String, VultrSnapShot> getSnapShots(VultrAccount va) {
		VultrRequest vr = new VultrRequest(va.getKey());
		VultrRequestResult vrr = vr.sendReq("snapshot/list", null);
		JSONObject jo = vrr.toJsonObject();
		if (vrr.getResponse().equals("[]")) {
			HashMap<String, VultrSnapShot> hm = new HashMap<>();
			return hm;
		}

		HashMap<String, VultrSnapShot> hm = new HashMap<>();
		for (Object ob : jo.keySet()) {
			JSONObject s = (JSONObject) jo.get(ob);
			hm.put((String) ob, new VultrSnapShot((String) ob, va).setData(s));
		}
		return hm;
	}
	
	public Boolean destroy(){
		VultrRequest vr = new VultrRequest(va.getKey());
		VultrRequestResult vrr = vr.sendReq("snapshot/destroy", "SNAPSHOTID="+id);
		return vrr.isOK();
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getDatecreated() {
		return datecreated;
	}

	public String getStatus() {
		return status;
	}

	public Long getSize() {
		return size;
	}

	public VultrAccount getAccount() {
		return va;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + ", ID: " + id + ", Description: " + description + ", Size: " + size;
	}

}
