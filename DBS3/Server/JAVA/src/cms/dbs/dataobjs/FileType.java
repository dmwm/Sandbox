/**
 * 
 $Revision: 1.6 $"
 $Id: FileType.java,v 1.6 2009/10/15 13:00:52 yuyi Exp $"
 *
 * Data Object from table : FILE_TYPES
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class FileType extends JSONObject  {

	public FileType ( ) {

	}
		
        public FileType ( int fileTypeID, String fileType ) throws Exception  {
		
                this.putOnce("FILE_TYPE_ID", fileTypeID );
                this.putOnce("FILE_TYPE", fileType );
        }

	public void setFileTypeID (int fileTypeID) throws Exception {
 		this.put( "FILE_TYPE_ID", fileTypeID );
	}
	
	public void setFileType (String fileType) throws Exception {
 		this.put( "FILE_TYPE", fileType );
	}
	
	public int getFileTypeID ( )  throws Exception {
		int fileTypeID = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_TYPE_ID"))) {
                       	fileTypeID = this.getInt("FILE_TYPE_ID");
               	}
                return fileTypeID;
        }
	
	public String getFileType ( )  throws Exception {
		String fileType = null;
               	if (!JSONObject.NULL.equals(this.get("FILE_TYPE"))) {
                       	fileType = this.getString("FILE_TYPE");
               	}
                return fileType;
        }
	
}
