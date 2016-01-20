package com.datastax.honeywell;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVRecord;

public class UIData {
	private	String		deviceid	;
	private	String		yyyymm	;
	private	Date		ts	;
	private	long		rawmessageid	;
	private	boolean		autocapableflag	;
	private	boolean		autochangeoveractive	;
	private	boolean		autoflag	;
	private	int			batterypowered	;
	private	int			batterystatus	;
	private	boolean		bloweronlyallowed	;
	private	boolean		circflag	;
	private	boolean		commcirccabable	;
	private	boolean		commercial	;
	private	BigDecimal		coollowersetptlimit	;
	private	int			coolnextperiod	;
	private	Date		coolnexttime	;
	private	BigDecimal		coolsetpoint	;
	private	BigDecimal		cooluppersetptlimit	;
	private	BigDecimal		deadband	;
	private	String		displayedunits	;
	private	BigDecimal		disptemperature	;
	private	String		disptemperaturestatus	;
	private	boolean		dmdrspnsowner	;
	private	boolean		dryallowed	;
	private	boolean		dualsetpointstatus	;
	private	boolean		emergencyheatactive	;
	private	int			extsysswitch	;
	private	int			fanswitchspeed	;
	private	boolean		globalflag	;
	private	BigDecimal		heatlowersetptlimit	;
	private	int			heatnextperiod	;
	private	Date		heatnexttime	;
	private	BigDecimal		heatsetpoint	;
	private	BigDecimal		heatuppersetptlimit	;
	private	boolean		holduntilcapable	;
	private	boolean		indatasession	;
	private	BigDecimal		indoorhumidity	;
	private	String		indoorhumidstatus	;
	private	int			msgversion	;
	private	int			numberofspeeds	;
	private	int			obpolarity	;
	private	BigDecimal		outdoorhumidity	;
	private	String		outdoorhumidstatus	;
	private	BigDecimal		outdoortemp	;
	private	String		outdoortempstatus	;
	private	boolean		ramprateowner	;
	private	boolean		rescirccapableflag	;
	private	BigDecimal		schedcoolsp	;
	private	BigDecimal		schedheatsp	;
	private	boolean		schedulecapable	;
	private	boolean		sensefromhereallowed	;
	private	boolean		sensoraveraging	;
	private	boolean		southernawayallowed	;
	private	boolean		southernawayallowed2	;
	private	BigDecimal		statsensedisptemp	;
	private	String		statuscool	;
	private	String		statusheat	;
	private	boolean		switchautoallowed	;
	private	boolean		switchcoolallowed	;
	private	boolean		switchemergencyheatallowed	;
	private	boolean		switchheatallowed	;
	private	boolean		switchoffallowed	;
	private	String		systemswitchposition	;
	private	boolean		thermostatlocked	;
	private	int			timeofdayflag	;
	private	String		uidataid	;
	private	int			userid	;
	private	int			vacationhold	;
	
	private DateFormat format_timestamp;
	private DateFormat format_yyyymm;
	

	public UIData(CSVRecord uidata_csv){
		// DateFormat = 3/18/15 23:59 = mm/dd/yy hh:mm
		format_timestamp = new SimpleDateFormat("MM/dd/yy HH:mm");
		format_yyyymm = new SimpleDateFormat("yyyyMM");
		
		this.setDeviceid(uidata_csv.get(0));
		this.setTs(uidata_csv.get(1));
		this.setUidataid(uidata_csv.get(2));
		this.setUserid(uidata_csv.get(3));
		this.setOutdoortemp(uidata_csv.get(4));
		this.setDisptemperature(uidata_csv.get(5));
		this.setHeatsetpoint(uidata_csv.get(6));
		this.setCoolsetpoint(uidata_csv.get(7));
		this.setDisplayedunits(uidata_csv.get(8));
		this.setStatusheat(uidata_csv.get(9));
		this.setStatuscool(uidata_csv.get(10));
		this.setHolduntilcapable(uidata_csv.get(11));
		this.setSchedulecapable(uidata_csv.get(12));
		this.setVacationhold(uidata_csv.get(13));
		this.setDualsetpointstatus(uidata_csv.get(14));
		this.setSensefromhereallowed(uidata_csv.get(15));
		this.setHeatlowersetptlimit(uidata_csv.get(16));
		this.setHeatuppersetptlimit(uidata_csv.get(17));
		this.setCoollowersetptlimit(uidata_csv.get(18));
		this.setCooluppersetptlimit(uidata_csv.get(19));
		this.setSchedheatsp(uidata_csv.get(20));
		this.setSchedcoolsp(uidata_csv.get(21));
		this.setSwitchautoallowed(uidata_csv.get(22));
		this.setSwitchcoolallowed(uidata_csv.get(23));
		this.setSwitchoffallowed(uidata_csv.get(24));
		this.setSwitchheatallowed(uidata_csv.get(25));
		this.setSwitchemergencyheatallowed(uidata_csv.get(26));
		this.setSystemswitchposition(uidata_csv.get(27));
		this.setSouthernawayallowed(uidata_csv.get(28));
		this.setObpolarity(uidata_csv.get(29));
		this.setThermostatlocked(uidata_csv.get(30));
		this.setBatterystatus(uidata_csv.get(31));
		this.setDeadband(uidata_csv.get(32));
		this.setOutdoorhumidity(uidata_csv.get(33));
		this.setIndoorhumidity(uidata_csv.get(34));
		this.setSensoraveraging(uidata_csv.get(35));
		this.setTimeofdayflag(uidata_csv.get(36));
		this.setStatsensedisptemp(uidata_csv.get(37));
		this.setCommercial(uidata_csv.get(38));
		this.setFanswitchspeed(uidata_csv.get(39));
		this.setCircflag(uidata_csv.get(40));
		this.setGlobalflag(uidata_csv.get(41));
		this.setRescirccapableflag(uidata_csv.get(42));
		this.setAutoflag(uidata_csv.get(43));
		this.setAutocapableflag(uidata_csv.get(44));
		this.setCommcirccabable(uidata_csv.get(45));
		this.setBatterypowered(uidata_csv.get(46));
		this.setNumberofspeeds(uidata_csv.get(47));
		this.setExtsysswitch(uidata_csv.get(48));
		this.setSouthernawayallowed2(uidata_csv.get(49));
		this.setBloweronlyallowed(uidata_csv.get(50));
		this.setDryallowed(uidata_csv.get(51));
		this.setRamprateowner(uidata_csv.get(52));
		this.setDmdrspnsowner(uidata_csv.get(53));
		this.setRawmessageid(uidata_csv.get(54));
		this.setMsgversion(uidata_csv.get(55));
		this.setOutdoortempstatus(uidata_csv.get(56));
		this.setDisptemperaturestatus(uidata_csv.get(57));
		this.setOutdoorhumidstatus(uidata_csv.get(58));
		this.setIndoorhumidstatus(uidata_csv.get(59));
		this.setCoolnexttime(uidata_csv.get(60));
		this.setHeatnexttime(uidata_csv.get(61));
		this.setAutochangeoveractive(uidata_csv.get(62));
		this.setEmergencyheatactive(uidata_csv.get(63));
		this.setCoolnextperiod(uidata_csv.get(64));
		this.setHeatnextperiod(uidata_csv.get(65));
		this.setIndatasession(uidata_csv.get(66));
	}
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getYyyymm() {
		return yyyymm;
	}
	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}
	public Date getTs() {
		return ts;
	}
	
	public void setTs(String string_ts) {
		try {
			this.ts = format_timestamp.parse(string_ts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Unable to parse timestamp: " + string_ts);
		}
		
		this.setYyyymm(format_yyyymm.format(ts));
	}
	
	public long getRawmessageid() {
		return rawmessageid;
	}
	
	public void setRawmessageid(String string_rawmessageid) {
		this.rawmessageid = Long.parseLong(string_rawmessageid);
	}
	
	public boolean isAutocapableflag() {
		return autocapableflag;
	}
	
	public void setAutocapableflag(String string_autocapableflag) {
		this.autocapableflag = Boolean.valueOf(string_autocapableflag);
	}
	
	public boolean isAutochangeoveractive() {
		return autochangeoveractive;
	}
	public void setAutochangeoveractive(String string_autochangeoveractive) {
		this.autochangeoveractive = Boolean.valueOf(string_autochangeoveractive);
	}
	
	public boolean isAutoflag() {
		return autoflag;
	}
	
	public void setAutoflag(String string_autoflag) {
		this.autoflag = Boolean.valueOf(string_autoflag);
	}
	
	public int getBatterypowered() {
		return batterypowered;
	}
	
	public void setBatterypowered(String string_batterypowered) {
		this.batterypowered = Integer.parseInt(string_batterypowered);
	}
	public int getBatterystatus() {
		return batterystatus;
	}
	public void setBatterystatus(String string_batterystatus) {
		this.batterystatus = Integer.parseInt(string_batterystatus);
	}
	public boolean isBloweronlyallowed() {
		return bloweronlyallowed;
	}
	public void setBloweronlyallowed(String string_bloweronlyallowed) {
		this.bloweronlyallowed = Boolean.valueOf(string_bloweronlyallowed);
	}
	public boolean isCircflag() {
		return circflag;
	}
	public void setCircflag(String string_circflag) {
		this.circflag = Boolean.valueOf(string_circflag);
	}
	public boolean isCommcirccabable() {
		return commcirccabable;
	}
	public void setCommcirccabable(String string_commcirccabable) {
		this.commcirccabable = Boolean.valueOf(string_commcirccabable);
	}
	public boolean isCommercial() {
		return commercial;
	}
	public void setCommercial(String string_commercial) {
		this.commercial = Boolean.valueOf(string_commercial);
	}
	public BigDecimal getCoollowersetptlimit() {
		return coollowersetptlimit;
	}
	public void setCoollowersetptlimit(String string_coollowersetptlimit) {
		this.coollowersetptlimit = new BigDecimal(string_coollowersetptlimit);
	}
	public int getCoolnextperiod() {
		return coolnextperiod;
	}
	public void setCoolnextperiod(String string_coolnextperiod) {
		if (string_coolnextperiod.equals("")) string_coolnextperiod = "0";
		this.coolnextperiod = Integer.parseInt(string_coolnextperiod);
	}
	public Date getCoolnexttime() {
		return coolnexttime;
	}
	public void setCoolnexttime(String string_coolnexttime) {
		try {
			this.coolnexttime = format_timestamp.parse(string_coolnexttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Unable to parse date coolnexttime: " + string_coolnexttime);
		}
	}
	public BigDecimal getCoolsetpoint() {
		return coolsetpoint;
	}
	public void setCoolsetpoint(String string_coolsetpoint) {
		this.coolsetpoint = new BigDecimal(string_coolsetpoint);
	}
	public BigDecimal getCooluppersetptlimit() {
		return cooluppersetptlimit;
	}
	public void setCooluppersetptlimit(String string_cooluppersetptlimit) {
		this.cooluppersetptlimit = new BigDecimal(string_cooluppersetptlimit);
	}
	public BigDecimal getDeadband() {
		return deadband;
	}
	public void setDeadband(String string_deadband) {
		this.deadband = new BigDecimal(string_deadband);
	}
	public String getDisplayedunits() {
		return displayedunits;
	}
	public void setDisplayedunits(String displayedunits) {
		this.displayedunits = displayedunits;
	}
	public BigDecimal getDisptemperature() {
		return disptemperature;
	}
	public void setDisptemperature(String string_disptemperature) {
		this.disptemperature = new BigDecimal(string_disptemperature);
	}
	public String getDisptemperaturestatus() {
		return disptemperaturestatus;
	}
	public void setDisptemperaturestatus(String disptemperaturestatus) {
		this.disptemperaturestatus = disptemperaturestatus;
	}
	public boolean isDmdrspnsowner() {
		return dmdrspnsowner;
	}
	public void setDmdrspnsowner(String string_dmdrspnsowner) {
		this.dmdrspnsowner = Boolean.valueOf(string_dmdrspnsowner);
	}
	public boolean isDryallowed() {
		return dryallowed;
	}
	public void setDryallowed(String string_dryallowed) {
		this.dryallowed = Boolean.valueOf(string_dryallowed);
	}
	public boolean isDualsetpointstatus() {
		return dualsetpointstatus;
	}
	public void setDualsetpointstatus(String string_dualsetpointstatus) {
		this.dualsetpointstatus = Boolean.valueOf(string_dualsetpointstatus);
	}
	public boolean isEmergencyheatactive() {
		return emergencyheatactive;
	}
	public void setEmergencyheatactive(String string_emergencyheatactive) {
		this.emergencyheatactive = Boolean.valueOf(string_emergencyheatactive);
	}
	public int getExtsysswitch() {
		return extsysswitch;
	}
	public void setExtsysswitch(String string_extsysswitch) {
		this.extsysswitch = Integer.parseInt(string_extsysswitch);
	}
	public int getFanswitchspeed() {
		return fanswitchspeed;
	}
	public void setFanswitchspeed(String string_fanswitchspeed) {
		this.fanswitchspeed = Integer.parseInt(string_fanswitchspeed);
	}
	public boolean isGlobalflag() {
		return globalflag;
	}
	public void setGlobalflag(String string_globalflag) {
		this.globalflag = Boolean.valueOf(string_globalflag);
	}
	public BigDecimal getHeatlowersetptlimit() {
		return heatlowersetptlimit;
	}
	public void setHeatlowersetptlimit(String string_heatlowersetptlimit) {
		this.heatlowersetptlimit = new BigDecimal(string_heatlowersetptlimit);
	}
	public int getHeatnextperiod() {
		return heatnextperiod;
	}
	public void setHeatnextperiod(String string_heatnextperiod) {
		if (string_heatnextperiod.equals("")) string_heatnextperiod = "0";
		this.heatnextperiod = Integer.parseInt(string_heatnextperiod);
	}
	public Date getHeatnexttime() {
		return heatnexttime;
	}
	
	public void setHeatnexttime(String string_heatnexttime) {
		try {
			this.heatnexttime = format_timestamp.parse(string_heatnexttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Unable to parse date heatnexttime: " + string_heatnexttime);
		}
	}
	
	public BigDecimal getHeatsetpoint() {
		return heatsetpoint;
	}
	public void setHeatsetpoint(String string_heatsetpoint) {
		this.heatsetpoint = new BigDecimal(string_heatsetpoint);
	}
	public BigDecimal getHeatuppersetptlimit() {
		return heatuppersetptlimit;
	}
	public void setHeatuppersetptlimit(String string_heatuppersetptlimit) {
		this.heatuppersetptlimit = new BigDecimal(string_heatuppersetptlimit);
	}
	public boolean isHolduntilcapable() {
		return holduntilcapable;
	}
	public void setHolduntilcapable(String string_holduntilcapable) {
		this.holduntilcapable = Boolean.valueOf(string_holduntilcapable);
	}
	public boolean isIndatasession() {
		return indatasession;
	}
	public void setIndatasession(String string_indatasession) {
		this.indatasession = Boolean.valueOf(string_indatasession);
	}
	public BigDecimal getIndoorhumidity() {
		return indoorhumidity;
	}
	public void setIndoorhumidity(String string_indoorhumidity) {
		this.indoorhumidity = new BigDecimal(string_indoorhumidity);
	}
	public String getIndoorhumidstatus() {
		return indoorhumidstatus;
	}
	public void setIndoorhumidstatus(String indoorhumidstatus) {
		this.indoorhumidstatus = indoorhumidstatus;
	}
	public int getMsgversion() {
		return msgversion;
	}
	public void setMsgversion(String string_msgversion) {
		this.msgversion = Integer.parseInt(string_msgversion);
	}
	public int getNumberofspeeds() {
		return numberofspeeds;
	}
	public void setNumberofspeeds(String string_numberofspeeds) {
		this.numberofspeeds = Integer.parseInt(string_numberofspeeds);
	}
	public int getObpolarity() {
		return obpolarity;
	}
	public void setObpolarity(String string_obpolarity) {
		this.obpolarity = Integer.parseInt(string_obpolarity);
	}
	public BigDecimal getOutdoorhumidity() {
		return outdoorhumidity;
	}
	public void setOutdoorhumidity(String string_outdoorhumidity) {
		this.outdoorhumidity = new BigDecimal(string_outdoorhumidity);
	}
	public String getOutdoorhumidstatus() {
		return outdoorhumidstatus;
	}
	public void setOutdoorhumidstatus(String outdoorhumidstatus) {
		this.outdoorhumidstatus = outdoorhumidstatus;
	}
	public BigDecimal getOutdoortemp() {
		return outdoortemp;
	}
	public void setOutdoortemp(String string_outdoortemp) {
		this.outdoortemp = new BigDecimal(string_outdoortemp);
	}
	public String getOutdoortempstatus() {
		return outdoortempstatus;
	}
	public void setOutdoortempstatus(String outdoortempstatus) {
		this.outdoortempstatus = outdoortempstatus;
	}
	public boolean isRamprateowner() {
		return ramprateowner;
	}
	public void setRamprateowner(String string_ramprateowner) {
		this.ramprateowner = Boolean.valueOf(string_ramprateowner);
	}
	public boolean isRescirccapableflag() {
		return rescirccapableflag;
	}
	public void setRescirccapableflag(String string_rescirccapableflag) {
		this.rescirccapableflag = Boolean.valueOf(string_rescirccapableflag);
	}
	public BigDecimal getSchedcoolsp() {
		return schedcoolsp;
	}
	public void setSchedcoolsp(String string_schedcoolsp) {
		this.schedcoolsp = new BigDecimal(string_schedcoolsp);
	}
	public BigDecimal getSchedheatsp() {
		return schedheatsp;
	}
	public void setSchedheatsp(String string_schedheatsp) {
		this.schedheatsp = new BigDecimal(string_schedheatsp);
	}
	public boolean isSchedulecapable() {
		return schedulecapable;
	}
	public void setSchedulecapable(String string_schedulecapable) {
		this.schedulecapable = Boolean.valueOf(string_schedulecapable);
	}
	public boolean isSensefromhereallowed() {
		return sensefromhereallowed;
	}
	public void setSensefromhereallowed(String string_sensefromhereallowed) {
		this.sensefromhereallowed = Boolean.valueOf(string_sensefromhereallowed);
	}
	public boolean isSensoraveraging() {
		return sensoraveraging;
	}
	public void setSensoraveraging(String string_sensoraveraging) {
		this.sensoraveraging = Boolean.valueOf(string_sensoraveraging);
	}
	public boolean isSouthernawayallowed() {
		return southernawayallowed;
	}
	public void setSouthernawayallowed(String string_southernawayallowed) {
		this.southernawayallowed = Boolean.valueOf(string_southernawayallowed);
	}
	public boolean isSouthernawayallowed2() {
		return southernawayallowed2;
	}
	public void setSouthernawayallowed2(String string_southernawayallowed2) {
		this.southernawayallowed2 = Boolean.valueOf(string_southernawayallowed2);
	}
	public BigDecimal getStatsensedisptemp() {
		return statsensedisptemp;
	}
	public void setStatsensedisptemp(String string_statsensedisptemp) {
		this.statsensedisptemp = new BigDecimal(string_statsensedisptemp);
	}
	public String getStatuscool() {
		return statuscool;
	}
	public void setStatuscool(String statuscool) {
		this.statuscool = statuscool;
	}
	public String getStatusheat() {
		return statusheat;
	}
	public void setStatusheat(String statusheat) {
		this.statusheat = statusheat;
	}
	public boolean isSwitchautoallowed() {
		return switchautoallowed;
	}
	public void setSwitchautoallowed(String string_switchautoallowed) {
		this.switchautoallowed = Boolean.valueOf(string_switchautoallowed);
	}
	public boolean isSwitchcoolallowed() {
		return switchcoolallowed;
	}
	public void setSwitchcoolallowed(String string_switchcoolallowed) {
		this.switchcoolallowed = Boolean.valueOf(string_switchcoolallowed);
	}
	public boolean isSwitchemergencyheatallowed() {
		return switchemergencyheatallowed;
	}
	public void setSwitchemergencyheatallowed(String string_switchemergencyheatallowed) {
		this.switchemergencyheatallowed = Boolean.valueOf(string_switchemergencyheatallowed);
	}
	public boolean isSwitchheatallowed() {
		return switchheatallowed;
	}
	public void setSwitchheatallowed(String string_switchheatallowed) {
		this.switchheatallowed = Boolean.valueOf(string_switchheatallowed);
	}
	public boolean isSwitchoffallowed() {
		return switchoffallowed;
	}
	public void setSwitchoffallowed(String string_switchoffallowed) {
		this.switchoffallowed = Boolean.valueOf(string_switchoffallowed);
	}
	public String getSystemswitchposition() {
		return systemswitchposition;
	}
	public void setSystemswitchposition(String systemswitchposition) {
		this.systemswitchposition = systemswitchposition;
	}
	public boolean isThermostatlocked() {
		return thermostatlocked;
	}
	public void setThermostatlocked(String string_thermostatlocked) {
		this.thermostatlocked = Boolean.valueOf(string_thermostatlocked);
	}
	public int getTimeofdayflag() {
		return timeofdayflag;
	}
	public void setTimeofdayflag(String string_timeofdayflag) {
		this.timeofdayflag = Integer.parseInt(string_timeofdayflag);
	}
	public String getUidataid() {
		return uidataid;
	}
	public void setUidataid(String uidataid) {
		this.uidataid = uidataid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(String string_userid) {
		this.userid = Integer.parseInt(string_userid);
	}
	public int getVacationhold() {
		return vacationhold;
	}
	public void setVacationhold(String string_vacationhold) {
		if (string_vacationhold.equals("TRUE")) string_vacationhold = "1";
		else if (string_vacationhold.equals("FALSE")) string_vacationhold = "0";
		
		this.vacationhold = Integer.parseInt(string_vacationhold);
	}
	
	
}
