/**
 * 
 $Revision: 1.5 $"
 $Id: DbsVersion.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : DBS_VERSIONS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class DbsVersion extends JSONObject  {

	public DbsVersion ( ) {

	}
		
        public DbsVersion ( int dbsVersionID, String schemaVersion, String dbsReleaseVersion, String instanceName, String instanceType, int creationDate, int lastModificationDate ) throws Exception  {
		
                this.putOnce("DBS_VERSION_ID", dbsVersionID );
                this.putOnce("SCHEMA_VERSION", schemaVersion );
                this.putOnce("DBS_RELEASE_VERSION", dbsReleaseVersion );
                this.putOnce("INSTANCE_NAME", instanceName );
                this.putOnce("INSTANCE_TYPE", instanceType );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("LAST_MODIFICATION_DATE", lastModificationDate );
        }

	public void setDbsVersionID (int dbsVersionID) throws Exception {
 		this.put( "DBS_VERSION_ID", dbsVersionID );
	}
	
	public void setSchemaVersion (String schemaVersion) throws Exception {
 		this.put( "SCHEMA_VERSION", schemaVersion );
	}
	
	public void setDbsReleaseVersion (String dbsReleaseVersion) throws Exception {
 		this.put( "DBS_RELEASE_VERSION", dbsReleaseVersion );
	}
	
	public void setInstanceName (String instanceName) throws Exception {
 		this.put( "INSTANCE_NAME", instanceName );
	}
	
	public void setInstanceType (String instanceType) throws Exception {
 		this.put( "INSTANCE_TYPE", instanceType );
	}
	
	public void setCreationDate (int creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setLastModificationDate (int lastModificationDate) throws Exception {
 		this.put( "LAST_MODIFICATION_DATE", lastModificationDate );
	}
	
	int getDbsVersionID ( )  throws Exception {
		int dbsVersionID = 0;
               	if (!JSONObject.NULL.equals(this.get("DBS_VERSION_ID"))) {
                       	dbsVersionID = this.getInt("DBS_VERSION_ID");
               	}
                return dbsVersionID;
        }
	
	String getSchemaVersion ( )  throws Exception {
		String schemaVersion = null;
               	if (!JSONObject.NULL.equals(this.get("SCHEMA_VERSION"))) {
                       	schemaVersion = this.getString("SCHEMA_VERSION");
               	}
                return schemaVersion;
        }
	
	String getDbsReleaseVersion ( )  throws Exception {
		String dbsReleaseVersion = null;
               	if (!JSONObject.NULL.equals(this.get("DBS_RELEASE_VERSION"))) {
                       	dbsReleaseVersion = this.getString("DBS_RELEASE_VERSION");
               	}
                return dbsReleaseVersion;
        }
	
	String getInstanceName ( )  throws Exception {
		String instanceName = null;
               	if (!JSONObject.NULL.equals(this.get("INSTANCE_NAME"))) {
                       	instanceName = this.getString("INSTANCE_NAME");
               	}
                return instanceName;
        }
	
	String getInstanceType ( )  throws Exception {
		String instanceType = null;
               	if (!JSONObject.NULL.equals(this.get("INSTANCE_TYPE"))) {
                       	instanceType = this.getString("INSTANCE_TYPE");
               	}
                return instanceType;
        }
	
	int getCreationDate ( )  throws Exception {
		int creationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("CREATION_DATE"))) {
                       	creationDate = this.getInt("CREATION_DATE");
               	}
                return creationDate;
        }
	
	int getLastModificationDate ( )  throws Exception {
		int lastModificationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("LAST_MODIFICATION_DATE"))) {
                       	lastModificationDate = this.getInt("LAST_MODIFICATION_DATE");
               	}
                return lastModificationDate;
        }
	
}