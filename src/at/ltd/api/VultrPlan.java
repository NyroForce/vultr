package at.ltd.api;

import java.util.HashMap;

import at.ltd.api.json.JSONObject;

public class VultrPlan {

	public VultrPlan(Integer id) {
		this.id = id;
	}

	private Integer id;
	private String name;
	private Integer cpu;
	private Integer ram;
	private Integer disk;
	private Double bandwidth;
	private Double price;
	private String plantype;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCpu() {
		return cpu;
	}

	public Integer getRam() {
		return ram;
	}

	public Integer getDisk() {
		return disk;
	}

	public Double getBandwidth() {
		return bandwidth;
	}

	public Double getPrice() {
		return price;
	}

	public String getPlantype() {
		return plantype;
	}

	public VultrPlan loadData() {
		VultrRequest vr = new VultrRequest(null);
		VultrRequestResult vrr = vr.sendReq("plans/list?type=vc2", null);
		JSONObject jo = vrr.toJsonObject();
		setData((JSONObject) jo.get(id));
		return this;
	}

	public VultrPlan setData(JSONObject jo) {
		name = (String) jo.get("name");
		cpu = Integer.valueOf(jo.get("vcpu_count").toString());
		ram = Integer.valueOf((String) jo.get("ram"));
		disk = Integer.valueOf((String) jo.get("disk"));
		bandwidth = Double.valueOf((String) jo.get("bandwidth"));
		price = Double.valueOf(jo.get("price_per_month").toString());
		plantype = (String) jo.get("plan_type");
		return this;
	}

	public static HashMap<Integer, VultrPlan> getPlans() {
		VultrRequest vr = new VultrRequest(null);
		VultrRequestResult vrr = vr.sendReq("plans/list?type=vc2", null);
		JSONObject jo = vrr.toJsonObject();
		HashMap<Integer, VultrPlan> hm = new HashMap<>();
		for (Object ob : jo.keySet()) {
			JSONObject s = (JSONObject) jo.get(ob);
			hm.put(Integer.valueOf((String) ob), new VultrPlan(Integer.valueOf((String) ob)).setData(s));
		}
		return hm;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ", ID: " + id + ", PRICE: " + price + ", NAME: " + name;
	}

}
