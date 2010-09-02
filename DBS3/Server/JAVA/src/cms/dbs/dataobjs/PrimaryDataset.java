/**
 * 
 $Revision: 1.7 $"
 $Id: PrimaryDataset.java,v 1.7 2009/10/13 15:37:31 yuyi Exp $"
 *
 * Data Object from table : PRIMARY_DATASETS
*/

package cms.dbs.dataobjs;

import cms.dbs.dataobjs.PrimaryDSType;
import org.json.JSONObject;
import org.json.JSONArray;

public class PrimaryDataset extends JSONObject {

	public PrimaryDataset ( ) {

	}

        public PrimaryDataset ( int primaryDSID, String primaryDSName, PrimaryDSType pType, int creationDate, String createBy ) throws Exception  {
		
                this.putOnce("PRIMARY_DS_ID", primaryDSID );
                this.putOnce("PRIMARY_DS_NAME",  primaryDSName );
                this.putOnce("PRIMARY_DS_TYPE_DO", pType );
                this.putOnce("CREATION_DATE",  creationDate );
                this.putOnce("CREATE_BY",  createBy );
        }

	public PrimaryDataset ( int primaryDSID, String primaryDSName) throws Exception  {

                this.putOnce("PRIMARY_DS_ID", primaryDSID );
                this.putOnce("PRIMARY_DS_NAME",  primaryDSName );
        }


        public void setPrimaryDSID (int primaryDSID) throws Exception {
                this.put( "PRIMARY_DS_ID", primaryDSID );
        }

        public void setPrimaryDSName (String primaryDSName) throws Exception {
                this.put( "PRIMARY_DS_NAME", primaryDSName );
        }

        public void setPrimaryDSTypeDO (PrimaryDSType primaryDSType) throws Exception {
                this.put( "PRIMARY_DS_TYPE_DO", primaryDSType );
        }
        public void setCreateBy (String createBy) throws Exception {
                this.put( "CREATE_BY", createBy );
        }

	public int getPrimaryDSID ( ) throws Exception{
		int primaryDSID = 0;
               	if (!JSONObject.NULL.equals(this.getInt("PRIMARY_DS_ID"))) {
                       	primaryDSID = this.getInt("PRIMARY_DS_ID");
               	}
                return primaryDSID;
        }
	
	public String getPrimaryDSName ( ) throws Exception{
		String primaryDSName = null;
               	if (!JSONObject.NULL.equals(this.getString("PRIMARY_DS_NAME"))) {
                       	primaryDSName =  this.getString("PRIMARY_DS_NAME");
               	}
                return primaryDSName;
        }
	
	public PrimaryDSType getPrimaryDSTypeDO ( ) throws Exception{
		PrimaryDSType primaryDSType = null;
               	if (! this.isNull("PRIMARY_DS_TYPE_DO")) {
                       	primaryDSType = (PrimaryDSType)this.getJSONObject("PRIMARY_DS_TYPE_DO");
               	}
                return primaryDSType;
        }
	
	 public int getCreationDate ( ) throws Exception{
		int creationDate = 0;
               	if (!JSONObject.NULL.equals(this.getInt("CREATION_DATE"))) {
                       	creationDate = this.getInt("CREATION_DATE");
               	}
                return creationDate;
        }
	
	public String getCreateBy ( ) throws Exception{
		String createBy = null;
               	if (!JSONObject.NULL.equals(this.getString("CREATE_BY"))) {
                       	createBy = this.getString("CREATE_BY");
               	}
                return createBy;
        }

	public void setCreationDate(int d) throws Exception{
	    this.put("CREATION_DATE", d);
	}	
	
}
