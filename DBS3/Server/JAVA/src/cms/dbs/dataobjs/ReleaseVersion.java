/**
 * 
 $Revision: 1.5 $"
 $Id: ReleaseVersion.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : RELEASE_VERSIONS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class ReleaseVersion extends JSONObject  {

	public ReleaseVersion ( ) {

	}
		
        public ReleaseVersion ( int releaseVersionID, String version ) throws Exception  {
		
                this.putOnce("RELEASE_VERSION_ID", releaseVersionID );
                this.putOnce("VERSION", version );
        }

	public void setReleaseVersionID (int releaseVersionID) throws Exception {
 		this.put( "RELEASE_VERSION_ID", releaseVersionID );
	}
	
	public void setVersion (String version) throws Exception {
 		this.put( "VERSION", version );
	}
	
	int getReleaseVersionID ( )  throws Exception {
		int releaseVersionID = 0;
               	if (!JSONObject.NULL.equals(this.get("RELEASE_VERSION_ID"))) {
                       	releaseVersionID = this.getInt("RELEASE_VERSION_ID");
               	}
                return releaseVersionID;
        }
	
	String getVersion ( )  throws Exception {
		String version = null;
               	if (!JSONObject.NULL.equals(this.get("VERSION"))) {
                       	version = this.getString("VERSION");
               	}
                return version;
        }
	
}