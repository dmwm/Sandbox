/**
 * 
 $Revision: 1.1 $"
 $Id: DatasetOutputModConfig.java,v 1.1 2009/10/02 18:56:14 afaq Exp $"
 *
 * Data Object from table : DATASET_OUTPUT_MOD_CONFIGS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class DatasetOutputModConfig extends JSONObject  {

	public DatasetOutputModConfig ( ) {

	}
		
        public DatasetOutputModConfig ( int dsOutputModConfID, int datasetID, int outputModConfigID ) throws Exception  {
		
                this.putOnce("DS_OUTPUT_MOD_CONF_ID", (Integer) dsOutputModConfID );
                this.putOnce("DATASET_ID", (Integer) datasetID );
                this.putOnce("OUTPUT_MOD_CONFIG_ID", (Integer) outputModConfigID );
        }

	int getDSOutputModConfID ( )  throws Exception {
		int dsOutputModConfID = 0;
               	if (!JSONObject.NULL.equals(this.get("DS_OUTPUT_MOD_CONF_ID"))) {
                       	dsOutputModConfID = (Integer) this.get("DS_OUTPUT_MOD_CONF_ID");
               	}
                return dsOutputModConfID;
        }
	
	int getDatasetID ( )  throws Exception {
		int datasetID = 0;
               	if (!JSONObject.NULL.equals(this.get("DATASET_ID"))) {
                       	datasetID = (Integer) this.get("DATASET_ID");
               	}
                return datasetID;
        }
	
	int getOutputModConfigID ( )  throws Exception {
		int outputModConfigID = 0;
               	if (!JSONObject.NULL.equals(this.get("OUTPUT_MOD_CONFIG_ID"))) {
                       	outputModConfigID = (Integer) this.get("OUTPUT_MOD_CONFIG_ID");
               	}
                return outputModConfigID;
        }
	
}
