/**
 * 
 $Revision: 1.3 $"
 $Id: OutputModuleConfig.java,v 1.3 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : OUTPUT_MODULE_CONFIGS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class OutputModuleConfig extends JSONObject  {

	public OutputModuleConfig ( ) {

	}
		
        public OutputModuleConfig ( int outputModConfigID, int appExecID, int releaseVersionID, int parameterSetHashID, String outputModuleLabel, int creationDate, String createBy ) throws Exception  {
		
                this.putOnce("OUTPUT_MOD_CONFIG_ID", outputModConfigID );
                this.putOnce("APP_EXEC_ID", appExecID );
                this.putOnce("RELEASE_VERSION_ID", releaseVersionID );
                this.putOnce("PARAMETER_SET_HASH_ID", parameterSetHashID );
                this.putOnce("OUTPUT_MODULE_LABEL", outputModuleLabel );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
        }

	public void setOutputModConfigID (int outputModConfigID) throws Exception {
 		this.put( "OUTPUT_MOD_CONFIG_ID", outputModConfigID );
	}
	
	public void setAppExecID (int appExecID) throws Exception {
 		this.put( "APP_EXEC_ID", appExecID );
	}
	
	public void setReleaseVersionID (int releaseVersionID) throws Exception {
 		this.put( "RELEASE_VERSION_ID", releaseVersionID );
	}
	
	public void setParameterSetHashID (int parameterSetHashID) throws Exception {
 		this.put( "PARAMETER_SET_HASH_ID", parameterSetHashID );
	}
	
	public void setOutputModuleLabel (String outputModuleLabel) throws Exception {
 		this.put( "OUTPUT_MODULE_LABEL", outputModuleLabel );
	}
	
	public void setCreationDate (int creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setCreateBy (String createBy) throws Exception {
 		this.put( "CREATE_BY", createBy );
	}
	
	int getOutputModConfigID ( )  throws Exception {
		int outputModConfigID = 0;
               	if (!JSONObject.NULL.equals(this.get("OUTPUT_MOD_CONFIG_ID"))) {
                       	outputModConfigID = this.getInt("OUTPUT_MOD_CONFIG_ID");
               	}
                return outputModConfigID;
        }
	
	int getAppExecID ( )  throws Exception {
		int appExecID = 0;
               	if (!JSONObject.NULL.equals(this.get("APP_EXEC_ID"))) {
                       	appExecID = this.getInt("APP_EXEC_ID");
               	}
                return appExecID;
        }
	
	int getReleaseVersionID ( )  throws Exception {
		int releaseVersionID = 0;
               	if (!JSONObject.NULL.equals(this.get("RELEASE_VERSION_ID"))) {
                       	releaseVersionID = this.getInt("RELEASE_VERSION_ID");
               	}
                return releaseVersionID;
        }
	
	int getParameterSetHashID ( )  throws Exception {
		int parameterSetHashID = 0;
               	if (!JSONObject.NULL.equals(this.get("PARAMETER_SET_HASH_ID"))) {
                       	parameterSetHashID = this.getInt("PARAMETER_SET_HASH_ID");
               	}
                return parameterSetHashID;
        }
	
	String getOutputModuleLabel ( )  throws Exception {
		String outputModuleLabel = null;
               	if (!JSONObject.NULL.equals(this.get("OUTPUT_MODULE_LABEL"))) {
                       	outputModuleLabel = this.getString("OUTPUT_MODULE_LABEL");
               	}
                return outputModuleLabel;
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
	
}