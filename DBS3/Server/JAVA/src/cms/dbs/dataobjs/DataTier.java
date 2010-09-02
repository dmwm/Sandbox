/**
 * 
 $Revision: 1.6 $"
 $Id: DataTier.java,v 1.6 2009/10/13 15:37:31 yuyi Exp $"
 *
 * Data Object from table : DATA_TIERS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class DataTier extends JSONObject  {

	public DataTier ( ) {

	}
		
        public DataTier ( int dataTierID, String dataTierName, int creationDate, String createBy ) throws Exception  {
		
                this.putOnce("DATA_TIER_ID", dataTierID );
                this.putOnce("DATA_TIER_NAME", dataTierName );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
        }

	public DataTier ( int dataTierID, String dataTierName) throws Exception  {

		this.putOnce("DATA_TIER_ID", dataTierID );
		this.putOnce("DATA_TIER_NAME", dataTierName );
	}
					
	public void setDataTierID (int dataTierID) throws Exception {
 		this.put( "DATA_TIER_ID", dataTierID );
	}
	
	public void setDataTierName (String dataTierName) throws Exception {
 		this.put( "DATA_TIER_NAME", dataTierName );
	}
	
	public void setCreationDate (int creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setCreateBy (String createBy) throws Exception {
 		this.put( "CREATE_BY", createBy );
	}
	
	public int getDataTierID ( )  throws Exception {
		int dataTierID = 0;
               	if (!JSONObject.NULL.equals(this.get("DATA_TIER_ID"))) {
                       	dataTierID = this.getInt("DATA_TIER_ID");
               	}
                return dataTierID;
        }
	
	public String getDataTierName ( )  throws Exception {
		String dataTierName = null;
               	if (!JSONObject.NULL.equals(this.get("DATA_TIER_NAME"))) {
                       	dataTierName = this.getString("DATA_TIER_NAME");
               	}
                return dataTierName;
        }
	
	public int getCreationDate ( )  throws Exception {
		int creationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("CREATION_DATE"))) {
                       	creationDate = this.getInt("CREATION_DATE");
               	}
                return creationDate;
        }
	
	public String getCreateBy ( )  throws Exception {
		String createBy = null;
               	if (!JSONObject.NULL.equals(this.get("CREATE_BY"))) {
                       	createBy = this.getString("CREATE_BY");
               	}
                return createBy;
        }
	
}
