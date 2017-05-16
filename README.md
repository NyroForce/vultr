# Vultr API
 **Simpl java API for the hoster: https://www.vultr.com/**<br />
 **(Only supports the Vultr Cloud Compute(VC2))**

## Features:
-	Manage Servers (Destroy, restart, start, stop(halt), reinstall, create snapshot)
-	Create Servers
-	Get Vultr Plans
-	Get SnapShots
-	Get VultrOperatingSystems
-	Get VulterRegions
-	Easy webAPI call 
-	Get Balance of the account

If you want to see more look at the source code.
## Example: 

~~~java	
//Create a VulterAccount instance ("API-KEY")
VultrAccount va = new VultrAccount("QR6XQYYBGK6EMUB52QPM5JBFFWGJTDCTR2YA");
int balance = va.getBalance();


/*
 * Returns a list of the currently bought servers. 
 * HashMap<Integer, VultrServer> 
 * Integer = Server id (DCID)
 */
HashMap<Integer, VultrServer> servers = VultrServer.getServers(va);


//Operatingsystems:
HashMap<Integer, VultrOperatingSystem> os = VultrOperatingSystem.getOSs();

//Plans:
HashMap<Integer, VultrPlan> plans = VultrPlan.getPlans();

//Regions:
HashMap<Integer, VultrRegion> region = VultrRegion.getRegions();

//Snapshots String = SnapShotID
HashMap<String, VultrSnapShot> snaps = VultrSnapShot.getSnapShots(va);


//##############
//Manage Servers
//##

//Make different things with the servers.
for(VultrServer vs : servers.values()){
	
	
	vs.stop();
	vs.start();
	vs.reboot();
	vs.reinstall();
	
	System.out.println("Cost: " + vs.getCost());
	System.out.println("ID: " + vs.getSUBID());
	System.out.println("IP: " + vs.getIp());
	System.out.println("Default Password: " + vs.getDefaultPassword());
	//Much more
	
	//destroy the server
	vs.destroy();
	
	
}

//##############
//Create Server
//##

VultrRegion myregion = null;

for(VultrRegion vr : region.values()){
	if(vr.getName().equals("Chicago")){
		myregion = vr;
		break;
	}
}

VultrPlan myplan = null;
for(VultrPlan vp : plans.values()){
	if(vp.getPrice() == 5){
		myplan = vp;
		break;
	}
}

VultrOperatingSystem myoperatingsystem = null;
for(VultrOperatingSystem vos : os.values()){
	if(vos.getName().equals("Debian 8 x64 (jessie)")){
		myoperatingsystem = vos;
		break;
	}
}

VultrCreateServer vcs = new VultrCreateServer(va);

VultrServer vs = vcs.createExtended(myregion, myplan, myoperatingsystem, null, null, "Test", "host123");

vs.loadData();

System.out.println("Server created! Info: " + vs.toString());


//#########
//API call
//##


//Api site: https://www.vultr.com/api/
//Now we reboot the server with a custom api call:

VultrRequest vr = new VultrRequest(va.getKey());
VultrRequestResult vrr = vr.sendReqS("server/reboot", "SUBID", vs.getSUBID());
//You also can send the URL request parameters manually vr.sendReq("server/reboot", "SUBID="+vs.getSUBID());

//Checks the HTTP Response Code
if(vrr.isOK()){
	System.out.println("Reboot done!");
	
}else{
	System.out.println("Error: " + vrr.getResponse());
}
~~~
    
    
## Console:

Cost: 5.00<br />
ID: 8511794<br />
IP: 45.63.77.18<br />
Default Password: L?b2ug)VKnRgqAdA<br />
Server created! Info: at.ltd.server.VultrServer, DCID: 2, LOCATION: Chicago, PLANID: 201, CPU: 1, RAM: 1024 MB, DISK: Virtual 25 GB<br />
Reboot done!
		

