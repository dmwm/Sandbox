/**
 * 
 $Revision: 1.6 $"
 $Id: ProcessedDataset.java,v 1.6 2009/10/13 15:37:31 yuyi Exp $"
 *
 * Data Object from table : PROCESSED_DATASETS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class ProcessedDataset extends JSONObject  {

	public ProcessedDataset ( ) {

	}
		
        public ProcessedDataset ( int processedDSID, String processedDSName ) throws Exception  {
		
                this.putOnce("PROCESSED_DS_ID", processedDSID );
                this.putOnce("PROCESSED_DS_NAME", processedDSName );
        }

	public void setProcessedDSID (int processedDSID) throws Exception {
 		this.put( "PROCESSED_DS_ID", processedDSID );
	}
	
	public void setProcessedDSName (String processedDSName) throws Exception {
 		this.put( "PROCESSED_DS_NAME", processedDSName );
	}
	
	public int getProcessedDSID ( )  throws Exception {
		int processedDSID = 0;
               	if (!JSONObject.NULL.equals(this.get("PROCESSED_DS_ID"))) {
                       	processedDSID = this.getInt("PROCESSED_DS_ID");
               	}
                return processedDSID;
        }
	
	public String getProcessedDSName ( )  throws Exception {
		String processedDSName = null;
               	if (!JSONObject.NULL.equals(this.get("PROCESSED_DS_NAME"))) {
                       	processedDSName = this.getString("PROCESSED_DS_NAME");
               	}
                return processedDSName;
        }
	
}
