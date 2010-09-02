/**
 * 
 $Revision: 1.5 $"
 $Id: AssociatedFile.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : ASSOCIATED_FILES
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class AssociatedFile extends JSONObject  {

	public AssociatedFile ( ) {

	}
		
        public AssociatedFile ( int assocatedFileID, int thisFileID, int assocatedFile ) throws Exception  {
		
                this.putOnce("ASSOCATED_FILE_ID", assocatedFileID );
                this.putOnce("THIS_FILE_ID", thisFileID );
                this.putOnce("ASSOCATED_FILE", assocatedFile );
        }

	public void setAssocatedFileID (int assocatedFileID) throws Exception {
 		this.put( "ASSOCATED_FILE_ID", assocatedFileID );
	}
	
	public void setThisFileID (int thisFileID) throws Exception {
 		this.put( "THIS_FILE_ID", thisFileID );
	}
	
	public void setAssocatedFile (int assocatedFile) throws Exception {
 		this.put( "ASSOCATED_FILE", assocatedFile );
	}
	
	int getAssocatedFileID ( )  throws Exception {
		int assocatedFileID = 0;
               	if (!JSONObject.NULL.equals(this.get("ASSOCATED_FILE_ID"))) {
                       	assocatedFileID = this.getInt("ASSOCATED_FILE_ID");
               	}
                return assocatedFileID;
        }
	
	int getThisFileID ( )  throws Exception {
		int thisFileID = 0;
               	if (!JSONObject.NULL.equals(this.get("THIS_FILE_ID"))) {
                       	thisFileID = this.getInt("THIS_FILE_ID");
               	}
                return thisFileID;
        }
	
	int getAssocatedFile ( )  throws Exception {
		int assocatedFile = 0;
               	if (!JSONObject.NULL.equals(this.get("ASSOCATED_FILE"))) {
                       	assocatedFile = this.getInt("ASSOCATED_FILE");
               	}
                return assocatedFile;
        }
	
}