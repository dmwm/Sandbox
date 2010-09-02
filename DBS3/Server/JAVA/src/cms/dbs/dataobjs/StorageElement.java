/**
 * 
 $Revision: 1.5 $"
 $Id: StorageElement.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : STORAGE_ELEMENTS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class StorageElement extends JSONObject  {

	public StorageElement ( ) {

	}
		
        public StorageElement ( int seID, String seName ) throws Exception  {
		
                this.putOnce("SE_ID", seID );
                this.putOnce("SE_NAME", seName );
        }

	public void setSeID (int seID) throws Exception {
 		this.put( "SE_ID", seID );
	}
	
	public void setSeName (String seName) throws Exception {
 		this.put( "SE_NAME", seName );
	}
	
	int getSeID ( )  throws Exception {
		int seID = 0;
               	if (!JSONObject.NULL.equals(this.get("SE_ID"))) {
                       	seID = this.getInt("SE_ID");
               	}
                return seID;
        }
	
	String getSeName ( )  throws Exception {
		String seName = null;
               	if (!JSONObject.NULL.equals(this.get("SE_NAME"))) {
                       	seName = this.getString("SE_NAME");
               	}
                return seName;
        }
	
}