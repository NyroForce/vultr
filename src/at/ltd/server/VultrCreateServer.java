package at.ltd.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import at.ltd.api.VultrAccount;
import at.ltd.api.VultrOperatingSystem;
import at.ltd.api.VultrPlan;
import at.ltd.api.VultrRegion;
import at.ltd.api.VultrRequest;
import at.ltd.api.VultrRequestResult;
import at.ltd.api.VultrSnapShot;

public class VultrCreateServer {

	public VultrCreateServer(VultrAccount va) {
		this.va = va;
	}
	private VultrAccount va;

	public VultrServer create(VultrRegion vr, VultrPlan vp, VultrOperatingSystem vos) {
		VultrServer vs = null;
		Integer DCID = vr.getId();
		Integer VPSPLANID = vp.getId();
		Integer OSID = vos.getId();
		VultrRequest vreq = new VultrRequest(va.getKey());
		VultrRequestResult vrr = vreq.sendReqS("server/create", "DCID", DCID, "VPSPLANID", VPSPLANID, "OSID", OSID);
		if (vrr.isOK()) {
			vs = new VultrServer(va, Integer.valueOf(vrr.toJsonObject().get("SUBID").toString()));
		}
		return vs;
	}

	public VultrServer create(Integer region, Integer planid, Integer opSystem) {
		VultrServer vs = null;
		VultrRequest vreq = new VultrRequest(va.getKey());
		VultrRequestResult vrr = vreq.sendReqS("server/create", "DCID", region, "VPSPLANID", planid, "OSID", opSystem);
		if (vrr.isOK()) {
			vs = new VultrServer(va, Integer.valueOf(vrr.toJsonObject().get("SUBID").toString()));
		}

		return vs;
	}

	/**
	 * Accepts null by all values after VultrOperatingSystem.
	 * 
	 * @param vr
	 * @param vp
	 * @param vos
	 * @return
	 */
	public VultrServer createExtended(VultrRegion vultrRegion, VultrPlan vpPlan, VultrOperatingSystem vultrOperatingSystem, VultrSnapShot vultrSnapShot, Integer script, String label, String hostname) {
		VultrServer vs = null;
		Integer DCID = vultrRegion.getId();
		Integer VPSPLANID = vpPlan.getId();
		Integer OSID = vultrOperatingSystem.getId();
		VultrRequest vreq = new VultrRequest(va.getKey());
		Map<String, String> hm = new HashMap<>();
		hm.put("DCID", DCID.toString());
		hm.put("VPSPLANID", VPSPLANID.toString());
		hm.put("OSID", OSID.toString());
		if (vultrSnapShot != null) {
			hm.put("SNAPSHOTID", vultrSnapShot.getId());
		}
		if (script != null) {
			hm.put("SCRIPTID ", script.toString());
		}
		if (label != null) {
			hm.put("label", label);
		}
		if (hostname != null) {
			hm.put("hostname", hostname);
		}

		String[] parms = new String[hm.size() * 2];
		int i = 0;
		for (String s : hm.keySet()) {
			parms[i] = s;
			i++;
			parms[i] = hm.get(s);
			i++;
		}
		
		VultrRequestResult vrr = vreq.sendReqS("server/create", parms);
		if (vrr.isOK()) {
			vs = new VultrServer(va, Integer.valueOf(vrr.toJsonObject().get("SUBID").toString()));
		}
		return vs;
	}
	
	
	/**
	 * Accepts null by all values after VultrOperatingSystem.
	 * 
	 * @param vr
	 * @param vp
	 * @param vos
	 * @return
	 */
	public VultrServer createExtended(Integer region, Integer planid, Integer opSystem, VultrSnapShot vultrSnapShot, Integer script, String label, String hostname) {
		VultrServer vs = null;
		VultrRequest vreq = new VultrRequest(va.getKey());
		Map<String, String> hm = new HashMap<>();
		hm.put("DCID", region.toString());
		hm.put("VPSPLANID", planid.toString());
		hm.put("OSID", opSystem.toString());
		if (vultrSnapShot != null) {
			hm.put("SNAPSHOTID", vultrSnapShot.getId());
		}
		if (script != null) {
			hm.put("SCRIPTID ", script.toString());
		}
		if (label != null) {
			hm.put("label", label);
		}
		if (hostname != null) {
			hm.put("hostname", hostname);
		}
		String[] parms = new String[hm.size() * 2];
		int i = 0;
		for (String s : hm.keySet()) {
			parms[i] = s;
			i++;
			parms[i] = hm.get(s);
			i++;
		}

		VultrRequestResult vrr = vreq.sendReqS("server/create", parms);
		if (vrr.isOK()) {
			vs = new VultrServer(va, Integer.valueOf(vrr.toJsonObject().get("SUBID").toString()));
		}
		return vs;
	}

	public VultrAccount getAccount() {
		return va;
	}

}
