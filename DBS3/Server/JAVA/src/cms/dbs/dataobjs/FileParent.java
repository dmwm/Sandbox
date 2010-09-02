/**
 * 
 $Revision: 1.7 $"
 $Id: FileParent.java,v 1.7 2009/10/19 14:59:42 yuyi Exp $"
 *
 * Data Object from table : FILE_PARENTS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;
import cms.dbs.dataobjs.File;

public class FileParent extends JSONObject  {

	public FileParent ( ) {

	}
		
        public FileParent ( int fileParentID, File child, File parent ) throws Exception  {
		
                this.putOnce("FILE_PARENT_ID", fileParentID );
                this.putOnce("THIS_FILE_DO", child );
                this.putOnce("PARENT_FILE_DO", parent);
        }


	public void setFileParentID (int fileParentID) throws Exception {
 		this.put( "FILE_PARENT_ID", fileParentID );
	}
	
	public void setThisFileDO (File thisFileDO) throws Exception {
 		this.put( "THIS_FILE_DO", thisFileDO );
	}
	public void setParentFileDO (File parentFileDO) throws Exception {
 		this.put( "PARENT_FILE_DO", parentFileDO );
	}
	public int getFileParentID ( )  throws Exception {
		int fileParentID = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_PARENT_ID"))) {
                       	fileParentID = this.getInt("FILE_PARENT_ID");
               	}
                return fileParentID;
        }
	
	public File getThisFileDO ( )  throws Exception {
		File thisFileDO = null;
               	if (!JSONObject.NULL.equals(this.get("THIS_FILE_DO"))) {
                       	thisFileDO = (File)this.getJSONObject("THIS_FILE_DO");
               	}
                return thisFileDO;
        }
	
	public File getParentFileDO ( )  throws Exception {
		File parentFileDO = null;
               	if (!JSONObject.NULL.equals(this.get("PARENT_FILE_DO"))) {
                       	parentFileDO = (File)this.getJSONObject("PARENT_FILE_DO");
               	}
                return parentFileDO;
        }
	
}
