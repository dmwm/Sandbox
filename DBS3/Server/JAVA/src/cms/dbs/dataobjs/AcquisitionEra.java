/**
 * 
 $Revision: 1.5 $"
 $Id: AcquisitionEra.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : ACQUISITION_ERAS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class AcquisitionEra extends JSONObject  {

	public AcquisitionEra ( ) {

	}
		
        public AcquisitionEra ( int acquisitionEraID, String acquisitionEraName, int creationDate, String createBy, String description ) throws Exception  {
		
                this.putOnce("ACQUISITION_ERA_ID", acquisitionEraID );
                this.putOnce("ACQUISITION_ERA_NAME", acquisitionEraName );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
                this.putOnce("DESCRIPTION", description );
        }

	public void setAcquisitionEraID (int acquisitionEraID) throws Exception {
 		this.put( "ACQUISITION_ERA_ID", acquisitionEraID );
	}
	
	public void setAcquisitionEraName (String acquisitionEraName) throws Exception {
 		this.put( "ACQUISITION_ERA_NAME", acquisitionEraName );
	}
	
	public void setCreationDate (int creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setCreateBy (String createBy) throws Exception {
 		this.put( "CREATE_BY", createBy );
	}
	
	public void setDescription (String description) throws Exception {
 		this.put( "DESCRIPTION", description );
	}
	
	int getAcquisitionEraID ( )  throws Exception {
		int acquisitionEraID = 0;
               	if (!JSONObject.NULL.equals(this.get("ACQUISITION_ERA_ID"))) {
                       	acquisitionEraID = this.getInt("ACQUISITION_ERA_ID");
               	}
                return acquisitionEraID;
        }
	
	String getAcquisitionEraName ( )  throws Exception {
		String acquisitionEraName = null;
               	if (!JSONObject.NULL.equals(this.get("ACQUISITION_ERA_NAME"))) {
                       	acquisitionEraName = this.getString("ACQUISITION_ERA_NAME");
               	}
                return acquisitionEraName;
        }
	
	int getCreationDate ( )  throws Exception {
		int creationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("CREATION_DATE"))) {
                       	creationDate = this.getInt("CREATION_DATE");
               	}
                return creationDate;
        }
	
	String getCreateBy ( )  throws Exception {
		String createBy = null;
               	if (!JSONObject.NULL.equals(this.get("CREATE_BY"))) {
                       	createBy = this.getString("CREATE_BY");
               	}
                return createBy;
        }
	
	String getDescription ( )  throws Exception {
		String description = null;
               	if (!JSONObject.NULL.equals(this.get("DESCRIPTION"))) {
                       	description = this.getString("DESCRIPTION");
               	}
                return description;
        }
	
}