package happy.happy3;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by Heaven on 8/14/2016.
 */
public class EventEntity extends GenericJson {
	@Key
	private Double rating;
	@Key
	private Integer type;
	@Key
	private Integer IndexInPeriod;
	@Key
	private Double longitude;
	@Key
	private Double latitude;
	@Key("_acl")
	private KinveyMetaData.AccessControlList acl;
	public EventEntity(){
	} //GenericJson classes must have a public empty constructor
}
