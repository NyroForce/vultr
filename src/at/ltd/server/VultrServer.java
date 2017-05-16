package at.ltd.server;

import java.util.HashMap;

import at.ltd.api.VultrAccount;
import at.ltd.api.VultrRequest;
import at.ltd.api.VultrRequestResult;
import at.ltd.api.VultrSnapShot;
import at.ltd.api.json.JSONObject;

public class VultrServer {

	public VultrServer(VultrAccount va, Integer SUBID) {
		this.SUBID = SUBID;
		this.account = va;
	}

	private VultrAccount account;
	private Integer SUBID;
	private String os;
	private String ram;
	private String disk;
	private String ip;
	private Integer cpu;
	private String location;
	private String cost;
	private Integer DCID;
	private String DefaultPassword;
	private String DateCreated;
	private String powerstatus;
	private Integer VPSPLANID;
	private String label;
	private Integer OSID;
	private String serverstate;

	public Boolean reinstall() {
		String key = getAccount().getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/reinstall", "SUBID=" + getSUBID());
		return vrr.isOK();
	}

	public Boolean reboot() {
		String key = getAccount().getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/reboot", "SUBID=" + getSUBID());
		return vrr.isOK();
	}

	public Boolean start() {
		String key = getAccount().getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/start", "SUBID=" + getSUBID());
		return vrr.isOK();
	}

	public Boolean stop() {
		String key = getAccount().getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/halt", "SUBID=" + getSUBID());
		return vrr.isOK();
	}
	
	public VultrSnapShot createSnapShot() {
		VultrRequest vr = new VultrRequest(getAccount().getKey());
		VultrRequestResult vrr = vr.sendReq("snapshot/create", "SUBID=" + getSUBID());
		if (vrr.isOK()) {
			JSONObject jo = vrr.toJsonObject();
			return new VultrSnapShot(jo.get("SNAPSHOTID").toString(), getAccount());
		}
		return null;

	}

	public Boolean destroy() {
		String key = getAccount().getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/destroy", "SUBID=" + SUBID);
		return vrr.isOK();
	}


	public VultrServer setData(JSONObject jo) {
		SUBID = Integer.valueOf(jo.get("SUBID").toString());
		DCID = Integer.valueOf(jo.get("DCID").toString());
		cpu = Integer.valueOf(jo.get("vcpu_count").toString());
		VPSPLANID = Integer.valueOf(jo.get("VPSPLANID").toString());
		OSID = Integer.valueOf(jo.get("OSID").toString());
		os = (String) jo.get("os");
		ram = (String) jo.get("ram");
		disk = (String) jo.get("disk");
		ip = (String) jo.get("main_ip");
		location = (String) jo.get("location");
		DefaultPassword = (String) jo.get("default_password");
		DateCreated = (String) jo.get("date_created");
		label = (String) jo.get("label");
		powerstatus = (String) jo.get("power_status");
		serverstate = (String) jo.get("server_state");
		cost = (String) jo.get("cost_per_month");
		return this;
	}

	public static HashMap<Integer, VultrServer> getServers(VultrAccount va) {
		String key = va.getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/list", null);
		if (vrr.getResponse().equals("[]")) {
			HashMap<Integer, VultrServer> hm = new HashMap<>();
			return hm;
		}
		JSONObject jo = vrr.toJsonObject();
		HashMap<Integer, VultrServer> hm = new HashMap<>();
		for (Object ob : jo.keySet()) {
			JSONObject s = (JSONObject) jo.get(ob);
			hm.put(Integer.valueOf((String) ob), new VultrServer(va, Integer.valueOf(ob.toString())).setData(s));
		}
		return hm;
	}

	public Boolean loadData() {
		String key = getAccount().getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/list", null);
		if (vrr.getResponse().equals("[]")) {
			return false;
		}
		JSONObject jo = vrr.toJsonObject();
		for (Object ob : jo.keySet()) {
			String s = ob.toString();
			if (s.equals(getSUBID().toString())) {
				setData((JSONObject) jo.get(ob));
				return true;
			}
		}
		return false;
	}

	public static VultrServer getServerByIP(VultrAccount va, String ip) {
		VultrServer vs = null;
		String key = va.getKey();
		VultrRequest vr = new VultrRequest(key);
		VultrRequestResult vrr = vr.sendReq("server/list", null);
		if (vrr.getResponse().equals("[]")) {
			return vs;
		}
		JSONObject jo = vrr.toJsonObject();
		for (Object ob : jo.keySet()) {
			JSONObject s = (JSONObject) jo.get(ob);
			String sip = (String) s.get("main_ip");
			if (sip.equals(ip)) {
				vs = new VultrServer(va, Integer.valueOf(ob.toString())).setData(s);
				return vs;
			}
		}

		return vs;
	}


	public VultrAccount getAccount() {
		return account;
	}

	public String getOs() {
		return os;
	}

	public String getRam() {
		return ram;
	}

	public String getDisk() {
		return disk;
	}

	public String getIp() {
		return ip;
	}

	public Integer getCpu() {
		return cpu;
	}

	public String getLocation() {
		return location;
	}

	public Integer getDCID() {
		return DCID;
	}

	public String getCost() {
		return cost;
	}

	public String getDefaultPassword() {
		return DefaultPassword;
	}

	public String getDateCreated() {
		return DateCreated;
	}

	public String getPowerstatus() {
		return powerstatus;
	}

	public String getServerstate() {
		return serverstate;
	}

	public Integer getVPSPLANID() {
		return VPSPLANID;
	}

	public String getLabel() {
		return label;
	}

	public Integer getOSID() {
		return OSID;
	}

	public Integer getSUBID() {
		return SUBID;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ", DCID: " + getDCID() + ", LOCATION: " + getLocation() + ", PLANID: " + getVPSPLANID() + ", CPU: " + getCpu() + ", RAM: " + getRam() + ", DISK: " + getDisk();
	}

}
