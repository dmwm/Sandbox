/**
 * 
 $Revision: 1.5 $"
 $Id: PrimaryDSType.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : PRIMARY_DS_TYPES
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;
import org.json.JSONArray;

public class PrimaryDSType extends JSONObject {

	public PrimaryDSType ( ) {

	}

        public PrimaryDSType ( int primaryDSTypeID, String primaryDSType ) throws Exception  {
		
                this.putOnce("PRIMARY_DS_TYPE_ID",  primaryDSTypeID );
                this.putOnce("PRIMARY_DS_TYPE",  primaryDSType );
        }

        public void setPrimaryDSTypeID (int primaryDSTypeID) throws Exception {
                this.put( "PRIMARY_DS_TYPE_ID", primaryDSTypeID );
        }

        public void setPrimaryDSType (String primaryDSType) throws Exception {
                this.put( "PRIMARY_DS_TYPE", primaryDSType );
        }


	public int getPrimaryDSTypeID ( ) throws Exception{
		int primaryDSTypeID = 0;
               	if (!JSONObject.NULL.equals(this.getInt("PRIMARY_DS_TYPE_ID"))) {
                       	primaryDSTypeID = this.getInt("PRIMARY_DS_TYPE_ID");
               	}
                return primaryDSTypeID;
        }
	
	public String getPrimaryDSType ( ) throws Exception{
		String primaryDSType = null;
               	if (!JSONObject.NULL.equals(this.getString("PRIMARY_DS_TYPE"))) {
                       	primaryDSType =  this.getString("PRIMARY_DS_TYPE");
               	}
                return primaryDSType;
        }
	
}
