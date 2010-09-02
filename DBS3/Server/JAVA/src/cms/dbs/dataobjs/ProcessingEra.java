/**
 * 
 $Revision: 1.5 $"
 $Id: ProcessingEra.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : PROCESSING_ERAS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class ProcessingEra extends JSONObject  {

	public ProcessingEra ( ) {

	}
		
        public ProcessingEra ( int processingEraID, String processingVersion, int creationDate, String createBy, String description ) throws Exception  {
		
                this.putOnce("PROCESSING_ERA_ID", processingEraID );
                this.putOnce("PROCESSING_VERSION", processingVersion );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
                this.putOnce("DESCRIPTION", description );
        }

	public void setProcessingEraID (int processingEraID) throws Exception {
 		this.put( "PROCESSING_ERA_ID", processingEraID );
	}
	
	public void setProcessingVersion (String processingVersion) throws Exception {
 		this.put( "PROCESSING_VERSION", processingVersion );
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
	
	int getProcessingEraID ( )  throws Exception {
		int processingEraID = 0;
               	if (!JSONObject.NULL.equals(this.get("PROCESSING_ERA_ID"))) {
                       	processingEraID = this.getInt("PROCESSING_ERA_ID");
               	}
                return processingEraID;
        }
	
	String getProcessingVersion ( )  throws Exception {
		String processingVersion = null;
               	if (!JSONObject.NULL.equals(this.get("PROCESSING_VERSION"))) {
                       	processingVersion = this.getString("PROCESSING_VERSION");
               	}
                return processingVersion;
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