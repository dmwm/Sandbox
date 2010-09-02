/**
 * 
 $Revision: 1.5 $"
 $Id: BlockParent.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : BLOCK_PARENTS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class BlockParent extends JSONObject  {

	public BlockParent ( ) {

	}
		
        public BlockParent ( int blockParentID, int thisBlockID, int parentBlockID ) throws Exception  {
		
                this.putOnce("BLOCK_PARENT_ID", blockParentID );
                this.putOnce("THIS_BLOCK_ID", thisBlockID );
                this.putOnce("PARENT_BLOCK_ID", parentBlockID );
        }

	public void setBlockParentID (int blockParentID) throws Exception {
 		this.put( "BLOCK_PARENT_ID", blockParentID );
	}
	
	public void setThisBlockID (int thisBlockID) throws Exception {
 		this.put( "THIS_BLOCK_ID", thisBlockID );
	}
	
	public void setParentBlockID (int parentBlockID) throws Exception {
 		this.put( "PARENT_BLOCK_ID", parentBlockID );
	}
	
	int getBlockParentID ( )  throws Exception {
		int blockParentID = 0;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_PARENT_ID"))) {
                       	blockParentID = this.getInt("BLOCK_PARENT_ID");
               	}
                return blockParentID;
        }
	
	int getThisBlockID ( )  throws Exception {
		int thisBlockID = 0;
               	if (!JSONObject.NULL.equals(this.get("THIS_BLOCK_ID"))) {
                       	thisBlockID = this.getInt("THIS_BLOCK_ID");
               	}
                return thisBlockID;
        }
	
	int getParentBlockID ( )  throws Exception {
		int parentBlockID = 0;
               	if (!JSONObject.NULL.equals(this.get("PARENT_BLOCK_ID"))) {
                       	parentBlockID = this.getInt("PARENT_BLOCK_ID");
               	}
                return parentBlockID;
        }
	
}