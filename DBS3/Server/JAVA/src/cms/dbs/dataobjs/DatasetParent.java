/**
 * 
 $Revision: 1.3 $"
 $Id: DatasetParent.java,v 1.3 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : DATASET_PARENTS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class DatasetParent extends JSONObject  {

	public DatasetParent ( ) {

	}
		
        public DatasetParent ( int datasetParentID, int thisDatasetID, int parentDatasetID ) throws Exception  {
		
                this.putOnce("DATASET_PARENT_ID", datasetParentID );
                this.putOnce("THIS_DATASET_ID", thisDatasetID );
                this.putOnce("PARENT_DATASET_ID", parentDatasetID );
        }

	public void setDatasetParentID (int datasetParentID) throws Exception {
 		this.put( "DATASET_PARENT_ID", datasetParentID );
	}
	
	public void setThisDatasetID (int thisDatasetID) throws Exception {
 		this.put( "THIS_DATASET_ID", thisDatasetID );
	}
	
	public void setParentDatasetID (int parentDatasetID) throws Exception {
 		this.put( "PARENT_DATASET_ID", parentDatasetID );
	}
	
	int getDatasetParentID ( )  throws Exception {
		int datasetParentID = 0;
               	if (!JSONObject.NULL.equals(this.get("DATASET_PARENT_ID"))) {
                       	datasetParentID = this.getInt("DATASET_PARENT_ID");
               	}
                return datasetParentID;
        }
	
	int getThisDatasetID ( )  throws Exception {
		int thisDatasetID = 0;
               	if (!JSONObject.NULL.equals(this.get("THIS_DATASET_ID"))) {
                       	thisDatasetID = this.getInt("THIS_DATASET_ID");
               	}
                return thisDatasetID;
        }
	
	int getParentDatasetID ( )  throws Exception {
		int parentDatasetID = 0;
               	if (!JSONObject.NULL.equals(this.get("PARENT_DATASET_ID"))) {
                       	parentDatasetID = this.getInt("PARENT_DATASET_ID");
               	}
                return parentDatasetID;
        }
	
}