package com.datastax.honeywell;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster.Builder;


public class DataStaxCluster {
	private String node;
	private String keyspace;
	private Cluster cluster;
	private Session session;
	
	//For writing each record into multiple partitions for searching
	private AdhocPartitioner partitioner;
	
	//private PreparedStatement ps_uidata_csv;
	//private PreparedStatement ps_uidata_yyyymm;
	private PreparedStatement psWriteDeviceIndex;
	private PreparedStatement psGetDevices;
	private PreparedStatement psGetMetrics;
	
	private PreparedStatement psGetCommTasks;
	private PreparedStatement psSaveCommTask;
	
	public DataStaxCluster(String node, String keyspace){
		setNode(node);
		setKeyspace(keyspace);
		
		partitioner = new AdhocPartitioner();
		
		connect();
		prepare();
	}	

	private void connect() {		
		Builder builder = Cluster.builder();
		builder.addContactPoints(node);
		
		cluster = builder.build();
		cluster.getConfiguration().getQueryOptions().setConsistencyLevel(ConsistencyLevel.ONE);
		session = cluster.connect(keyspace);
	}
	
	private void prepare() {
		//ps_uidata_csv = session.prepare("insert into uidata_csv(deviceid,	ts,	rawmessageid,	autocapableflag,	autochangeoveractive,	autoflag,	batterypowered,	batterystatus,	bloweronlyallowed,	circflag,	commcirccabable,	commercial,	coollowersetptlimit,	coolnextperiod,	coolnexttime,	coolsetpoint,	cooluppersetptlimit,	deadband,	displayedunits,	disptemperature,	disptemperaturestatus,	dmdrspnsowner,	dryallowed,	dualsetpointstatus,	emergencyheatactive,	extsysswitch,	fanswitchspeed,	globalflag,	heatlowersetptlimit,	heatnextperiod,	heatnexttime,	heatsetpoint,	heatuppersetptlimit,	holduntilcapable,	indatasession,	indoorhumidity,	indoorhumidstatus,	msgversion,	numberofspeeds,	obpolarity,	outdoorhumidity,	outdoorhumidstatus,	outdoortemp,	outdoortempstatus,	ramprateowner,	rescirccapableflag,	schedcoolsp,	schedheatsp,	schedulecapable,	sensefromhereallowed,	sensoraveraging,	southernawayallowed,	southernawayallowed2,	statsensedisptemp,	statuscool,	statusheat,	switchautoallowed,	switchcoolallowed,	switchemergencyheatallowed,	switchheatallowed,	switchoffallowed,	systemswitchposition,	thermostatlocked,	timeofdayflag,	uidataid,	userid,	vacationhold) values(?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?)");
		//ps_uidata_yyyymm = session.prepare("insert into uidata_yyyymm(deviceid,	yyyymm,	ts,	rawmessageid,	autocapableflag,	autochangeoveractive,	autoflag,	batterypowered,	batterystatus,	bloweronlyallowed,	circflag,	commcirccabable,	commercial,	coollowersetptlimit,	coolnextperiod,	coolnexttime,	coolsetpoint,	cooluppersetptlimit,	deadband,	displayedunits,	disptemperature,	disptemperaturestatus,	dmdrspnsowner,	dryallowed,	dualsetpointstatus,	emergencyheatactive,	extsysswitch,	fanswitchspeed,	globalflag,	heatlowersetptlimit,	heatnextperiod,	heatnexttime,	heatsetpoint,	heatuppersetptlimit,	holduntilcapable,	indatasession,	indoorhumidity,	indoorhumidstatus,	msgversion,	numberofspeeds,	obpolarity,	outdoorhumidity,	outdoorhumidstatus,	outdoortemp,	outdoortempstatus,	ramprateowner,	rescirccapableflag,	schedcoolsp,	schedheatsp,	schedulecapable,	sensefromhereallowed,	sensoraveraging,	southernawayallowed,	southernawayallowed2,	statsensedisptemp,	statuscool,	statusheat,	switchautoallowed,	switchcoolallowed,	switchemergencyheatallowed,	switchheatallowed,	switchoffallowed,	systemswitchposition,	thermostatlocked,	timeofdayflag,	uidataid,	userid,	vacationhold) values(?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?)");
		psWriteDeviceIndex = session.prepare("insert into device_index(device_type,firmware_version,monitored_group,os_index,power_type,mac_id) values(?,?,?,?,?,?)");
		psGetDevices = session.prepare("select mac_id from device_index where device_type=? and firmware_version=? and monitored_group=? and os_index=? and power_type=?");
		psGetMetrics = session.prepare("select * from metrics_by_mac where metric=? and mac_id=? and date >= ? and date <= ?");
		
		//CommTasks
		psGetCommTasks = session.prepare("select * from commtask_duration where mac_id=? and date >= ? and date <= ?");
		psSaveCommTask = session.prepare("insert into wo_successfull_commtask_duration(device_type, firmware_version, monitored_group, os_index, power_type, start_date, end_date, rank, mac_id, total_duration_in_millisecs, commtask_count, average_duration) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	}
	
	public void writeRecord(UIData uidata){
		//session.executeAsync(ps_uidata_csv.bind(uidata.getDeviceid(),	uidata.getTs(),	uidata.getRawmessageid(),	uidata.isAutocapableflag(),	uidata.isAutochangeoveractive(),	uidata.isAutoflag(),	uidata.getBatterypowered(),	uidata.getBatterystatus(),	uidata.isBloweronlyallowed(),	uidata.isCircflag(),	uidata.isCommcirccabable(),	uidata.isCommercial(),	uidata.getCoollowersetptlimit(),	uidata.getCoolnextperiod(),	uidata.getCoolnexttime(),	uidata.getCoolsetpoint(),	uidata.getCooluppersetptlimit(),	uidata.getDeadband(),	uidata.getDisplayedunits(),	uidata.getDisptemperature(),	uidata.getDisptemperaturestatus(),	uidata.isDmdrspnsowner(),	uidata.isDryallowed(),	uidata.isDualsetpointstatus(),	uidata.isEmergencyheatactive(),	uidata.getExtsysswitch(),	uidata.getFanswitchspeed(),	uidata.isGlobalflag(),	uidata.getHeatlowersetptlimit(),	uidata.getHeatnextperiod(),	uidata.getHeatnexttime(),	uidata.getHeatsetpoint(),	uidata.getHeatuppersetptlimit(),	uidata.isHolduntilcapable(),	uidata.isIndatasession(),	uidata.getIndoorhumidity(),	uidata.getIndoorhumidstatus(),	uidata.getMsgversion(),	uidata.getNumberofspeeds(),	uidata.getObpolarity(),	uidata.getOutdoorhumidity(),	uidata.getOutdoorhumidstatus(),	uidata.getOutdoortemp(),	uidata.getOutdoortempstatus(),	uidata.isRamprateowner(),	uidata.isRescirccapableflag(),	uidata.getSchedcoolsp(),	uidata.getSchedheatsp(),	uidata.isSchedulecapable(),	uidata.isSensefromhereallowed(),	uidata.isSensoraveraging(),	uidata.isSouthernawayallowed(),	uidata.isSouthernawayallowed2(),	uidata.getStatsensedisptemp(),	uidata.getStatuscool(),	uidata.getStatusheat(),	uidata.isSwitchautoallowed(),	uidata.isSwitchcoolallowed(),	uidata.isSwitchemergencyheatallowed(),	uidata.isSwitchheatallowed(),	uidata.isSwitchoffallowed(),	uidata.getSystemswitchposition(),	uidata.isThermostatlocked(),	uidata.getTimeofdayflag(),	uidata.getUidataid(),	uidata.getUserid(),	uidata.getVacationhold()));
		//session.executeAsync(ps_uidata_yyyymm.bind(uidata.getDeviceid(),	uidata.getYyyymm(),	uidata.getTs(),	uidata.getRawmessageid(),	uidata.isAutocapableflag(),	uidata.isAutochangeoveractive(),	uidata.isAutoflag(),	uidata.getBatterypowered(),	uidata.getBatterystatus(),	uidata.isBloweronlyallowed(),	uidata.isCircflag(),	uidata.isCommcirccabable(),	uidata.isCommercial(),	uidata.getCoollowersetptlimit(),	uidata.getCoolnextperiod(),	uidata.getCoolnexttime(),	uidata.getCoolsetpoint(),	uidata.getCooluppersetptlimit(),	uidata.getDeadband(),	uidata.getDisplayedunits(),	uidata.getDisptemperature(),	uidata.getDisptemperaturestatus(),	uidata.isDmdrspnsowner(),	uidata.isDryallowed(),	uidata.isDualsetpointstatus(),	uidata.isEmergencyheatactive(),	uidata.getExtsysswitch(),	uidata.getFanswitchspeed(),	uidata.isGlobalflag(),	uidata.getHeatlowersetptlimit(),	uidata.getHeatnextperiod(),	uidata.getHeatnexttime(),	uidata.getHeatsetpoint(),	uidata.getHeatuppersetptlimit(),	uidata.isHolduntilcapable(),	uidata.isIndatasession(),	uidata.getIndoorhumidity(),	uidata.getIndoorhumidstatus(),	uidata.getMsgversion(),	uidata.getNumberofspeeds(),	uidata.getObpolarity(),	uidata.getOutdoorhumidity(),	uidata.getOutdoorhumidstatus(),	uidata.getOutdoortemp(),	uidata.getOutdoortempstatus(),	uidata.isRamprateowner(),	uidata.isRescirccapableflag(),	uidata.getSchedcoolsp(),	uidata.getSchedheatsp(),	uidata.isSchedulecapable(),	uidata.isSensefromhereallowed(),	uidata.isSensoraveraging(),	uidata.isSouthernawayallowed(),	uidata.isSouthernawayallowed2(),	uidata.getStatsensedisptemp(),	uidata.getStatuscool(),	uidata.getStatusheat(),	uidata.isSwitchautoallowed(),	uidata.isSwitchcoolallowed(),	uidata.isSwitchemergencyheatallowed(),	uidata.isSwitchheatallowed(),	uidata.isSwitchoffallowed(),	uidata.getSystemswitchposition(),	uidata.isThermostatlocked(),	uidata.getTimeofdayflag(),	uidata.getUidataid(),	uidata.getUserid(),	uidata.getVacationhold()));		
		System.out.println("Old example. Uncomment to use.");
	}
	
	public void buildDeviceIndex(){
		ResultSet results = session.execute("select * from devices");
		//device_type,firmware_version,monitored_group,os_index,power_type
		
		for (Row row: results){			
			Set<ArrayList<Object>> inserts = partitioner.createPartitions(row.getObject("device_type"), 
					row.getObject("firmware_version"), 
					row.getObject("monitored_group"), 
					row.getObject("os_index"), 
					row.getObject("power_type"));
			
			for (ArrayList<Object> insertcols : inserts){
				session.executeAsync(psWriteDeviceIndex.bind(insertcols.get(0), 
						insertcols.get(1), 
						insertcols.get(2), 
						insertcols.get(3),
						insertcols.get(4),
						row.getObject("mac_id")));
			}
		}
	}
	
	public WorstOffenders getWorstOffenders(String device_type, String firmware_version, String monitored_group, String os_index, String power_type, Date startdate, Date enddate, String metric, int numberOffenders){
		WorstOffenders worstoffenders = new WorstOffenders(numberOffenders);
		List<ResultSetFuture> futures = new ArrayList<>();
		
		//Get the matching mac_ids
		ResultSet results = session.execute(psGetDevices.bind(device_type,firmware_version,monitored_group,os_index,power_type));
		
		//For each mac_id, query for the metric and date range
		for (Row row : results){
			futures.add(session.executeAsync(psGetMetrics.bind(metric, row.getString("mac_id"), startdate, enddate)));
		}
		
		//Summarize the metric for each result
		//For this example, we are counting the occurances.
		for (ResultSetFuture future : futures){
			ResultSet rows = future.getUninterruptibly();
			Offender offender = null;
			for (Row row : rows){
				if (null == offender){
					offender = new Offender(row.getString("mac_id"));
				}
				offender.increment();
			}
			if (null != offender){
				worstoffenders.add(offender);
			}
		}		
		
		return worstoffenders;
	}
	
	
//	For commtasks, use the same index table to find matching mac addresses, then sum the duration for each date range.
//	
//	CREATE TABLE commtask_duration
//	(
//	       mac_id text,
//	       date timestamp,
//	       duration_in_millisecs bigint,
//	       PRIMARY KEY ((mac_id), date)
//	);	

	public CommTaskRanking getCommTasks(String device_type, String firmware_version, String monitored_group, String os_index, String power_type, Date startdate, Date enddate, int limit){
		CommTaskRanking commTasks = new CommTaskRanking(limit);
		List<ResultSetFuture> futures = new ArrayList<>();
		
		//Get the matching mac_ids
		ResultSet results = session.execute(psGetDevices.bind(device_type,firmware_version,monitored_group,os_index,power_type));
		
		//For each mac_id, query for the date range and date range
		for (Row row : results){
			futures.add(session.executeAsync(psGetCommTasks.bind(row.getString("mac_id"), startdate, enddate)));
		}
		
		//Summarize the metric for each result
		//For this example, we are counting the occurances.
		for (ResultSetFuture future : futures){
			ResultSet rows = future.getUninterruptibly();
			CommTask ct = null;
			for (Row row : rows){
				if (null == ct){
					ct = new CommTask(row.getString("mac_id"));
				}
				ct.increment(row.getDouble("duration_in_millisecs"));
			}
			if (null != ct){
				commTasks.add(ct);
			}
		}
		
		//Save the calculated rankings
		saveCommTaskDuration(device_type, firmware_version, monitored_group, os_index, power_type, startdate, enddate, commTasks);		
		
		return commTasks;
	}	
	

// Save commtask queries
//	
//	CREATE TABLE wo_successfull_commtask_duration
//	(
//	       origin text,
//	       gateway_type text,
//	       firmware_version text,
//	       power_type text,
//	       start_date timestamp,
//	       end_date timestamp,
//	       rank int,
//	       mac_id text,
//	       total_duration_in_millisecs bigint,
//	       commtask_count bigint,
//	       average_duration decimal,
//	       PRIMARY KEY ((origin, gateway_type, firmware_version, power_type), start_day, end_day, rank)
//	);
//
//  insert into wo_successfull_commtask_duration(device_type, firmware_version, monitored_group, os_index, power_type, start_date, end_date, rank, mac_id, total_duration_in_millisecs, commtask_count, average_duration) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
	
	public void saveCommTaskDuration(String device_type, String firmware_version, String monitored_group, String os_index, String power_type, Date startdate, Date enddate, CommTaskRanking commTasks){
		int rank=1;
		for (CommTask commtask: commTasks.getCommTasks()){
			session.executeAsync(psSaveCommTask.bind(device_type, firmware_version, monitored_group, os_index, power_type, startdate, enddate, rank, commtask.getMac_id(), commtask.getDuration(), commtask.getCommTasks(), commtask.getAverageCommTaskDuration()));
			rank++;
		}
	}

	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}
	
}

