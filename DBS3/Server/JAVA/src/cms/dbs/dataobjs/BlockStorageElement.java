/**
 * 
 $Revision: 1.5 $"
 $Id: BlockStorageElement.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : BLOCK_STORAGE_ELEMENTS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class BlockStorageElement extends JSONObject  {

	public BlockStorageElement ( ) {

	}
		
        public BlockStorageElement ( int blockSeID, int seID, int blockID ) throws Exception  {
		
                this.putOnce("BLOCK_SE_ID", blockSeID );
                this.putOnce("SE_ID", seID );
                this.putOnce("BLOCK_ID", blockID );
        }

	public void setBlockSeID (int blockSeID) throws Exception {
 		this.put( "BLOCK_SE_ID", blockSeID );
	}
	
	public void setSeID (int seID) throws Exception {
 		this.put( "SE_ID", seID );
	}
	
	public void setBlockID (int blockID) throws Exception {
 		this.put( "BLOCK_ID", blockID );
	}
	
	int getBlockSeID ( )  throws Exception {
		int blockSeID = 0;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_SE_ID"))) {
                       	blockSeID = this.getInt("BLOCK_SE_ID");
               	}
                return blockSeID;
        }
	
	int getSeID ( )  throws Exception {
		int seID = 0;
               	if (!JSONObject.NULL.equals(this.get("SE_ID"))) {
                       	seID = this.getInt("SE_ID");
               	}
                return seID;
        }
	
	int getBlockID ( )  throws Exception {
		int blockID = 0;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_ID"))) {
                       	blockID = this.getInt("BLOCK_ID");
               	}
                return blockID;
        }
	
}