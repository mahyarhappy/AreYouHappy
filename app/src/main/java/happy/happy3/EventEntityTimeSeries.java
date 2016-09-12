package happy.happy3;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by Heaven on 8/14/2016.
 */
public class EventEntityTimeSeries extends GenericJson {
	@Key
	private Double rating;
	@Key
	private Integer minute;
	@Key
	private Integer minutecut;
	@Key
	private Integer hour;
	@Key
	private Integer hourcut;
	@Key
	private Integer day;
	@Key
	private Integer month;
	@Key
	private Integer year;
	@Key
	private Integer IndexInPeriod;
	@Key
	private Double longitude;
	@Key
	private Double latitude;
	@Key
	private Integer type;
	@Key
	private Integer LastData;
	@Key
	private Long date1;
	@Key
	private Long xlabel;
	@Key

	private Integer WhyPosition;
	@Key("_acl")
	private KinveyMetaData.AccessControlList acl;

	//event1.set("WhyPosition", cursor.getInt(cursor.getColumnIndex("WhyPosition")));
	public EventEntityTimeSeries(){


	} //GenericJson classes must have a public empty constructor
}
