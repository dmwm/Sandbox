/**
 * 
 $Revision: 1.1 $"
 $Id: FileOutputModConfig.java,v 1.1 2009/10/02 18:56:14 afaq Exp $"
 *
 * Data Object from table : FILE_OUTPUT_MOD_CONFIGS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class FileOutputModConfig extends JSONObject  {

	public FileOutputModConfig ( ) {

	}
		
        public FileOutputModConfig ( int fileOutputConfigID, int fileID, int outputModConfigID ) throws Exception  {
		
                this.putOnce("FILE_OUTPUT_CONFIG_ID", (Integer) fileOutputConfigID );
                this.putOnce("FILE_ID", (Integer) fileID );
                this.putOnce("OUTPUT_MOD_CONFIG_ID", (Integer) outputModConfigID );
        }

	int getFileOutputConfigID ( )  throws Exception {
		int fileOutputConfigID = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_OUTPUT_CONFIG_ID"))) {
                       	fileOutputConfigID = (Integer) this.get("FILE_OUTPUT_CONFIG_ID");
               	}
                return fileOutputConfigID;
        }
	
	int getFileID ( )  throws Exception {
		int fileID = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_ID"))) {
                       	fileID = (Integer) this.get("FILE_ID");
               	}
                return fileID;
        }
	
	int getOutputModConfigID ( )  throws Exception {
		int outputModConfigID = 0;
               	if (!JSONObject.NULL.equals(this.get("OUTPUT_MOD_CONFIG_ID"))) {
                       	outputModConfigID = (Integer) this.get("OUTPUT_MOD_CONFIG_ID");
               	}
                return outputModConfigID;
        }
	
}
