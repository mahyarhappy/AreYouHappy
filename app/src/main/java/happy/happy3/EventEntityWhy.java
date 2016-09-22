package happy.happy3;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Created by Heaven on 8/14/2016.
 */
public class EventEntityWhy extends GenericJson {
	@Key
	private Integer whyindex;
	@Key
	private Integer whycount;
	@Key
	private Integer whypercent;
	@Key
	private Integer ostan;
	@Key
	private String whystring;
	@Key("_acl")
	private KinveyMetaData.AccessControlList acl;

	public EventEntityWhy(){

	} //GenericJson classes must have a public empty constructor
}
