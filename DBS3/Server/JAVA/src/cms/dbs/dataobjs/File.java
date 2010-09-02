/**
 * 
 $Revision: 1.9 $"
 $Id: File.java,v 1.9 2009/11/30 22:09:00 afaq Exp $"
 *
 * Data Object from table : FILES
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;
import cms.dbs.dataobjs.Block;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.dataobjs.FileType;
import cms.dbs.dataobjs.BranchHash;

public class File extends JSONObject  {

	public File ( ) {

	}
		
        public File ( int fileID, String logicalFileName, int isFileValid, Dataset datasetDO, Block blockDO, 
	FileType fileTypeDO, String checkSum, int eventCount, int fileSize, BranchHash branchHashDO, String adler32, String md5, 
	double autoCrossSection, long creationDate, String createBy, long lastModificationDate, String lastModifiedBy ) throws Exception  {
		
                this.putOnce("FILE_ID", fileID );
                this.putOnce("LOGICAL_FILE_NAME", logicalFileName );
                this.putOnce("IS_FILE_VALID", isFileValid );
                this.putOnce("DATASET_DO", datasetDO );
                this.putOnce("BLOCK_DO", blockDO );
                this.putOnce("FILE_TYPE_DO", fileTypeDO );
                this.putOnce("CHECK_SUM", checkSum );
                this.putOnce("EVENT_COUNT", eventCount );
                this.putOnce("FILE_SIZE", fileSize );
                this.putOnce("BRANCH_HASH_DO", branchHashDO );
                this.putOnce("ADLER32", adler32 );
                this.putOnce("MD5", md5 );
                this.putOnce("AUTO_CROSS_SECTION", autoCrossSection );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
                this.putOnce("LAST_MODIFICATION_DATE", lastModificationDate );
                this.putOnce("LAST_MODIFIED_BY", lastModifiedBy );
        }

        public File ( int fileID, String logicalFileName, int isFileValid, 
	String checkSum, int eventCount, int fileSize, String adler32, String md5, 
	double autoCrossSection, long creationDate, String createBy, long lastModificationDate, String lastModifiedBy ) throws Exception  {
		
                this.putOnce("FILE_ID", fileID );
                this.putOnce("LOGICAL_FILE_NAME", logicalFileName );
                this.putOnce("IS_FILE_VALID", isFileValid );
                this.putOnce("CHECK_SUM", checkSum );
                this.putOnce("EVENT_COUNT", eventCount );
                this.putOnce("FILE_SIZE", fileSize );
                this.putOnce("ADLER32", adler32 );
                this.putOnce("MD5", md5 );
                this.putOnce("AUTO_CROSS_SECTION", autoCrossSection );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
                this.putOnce("LAST_MODIFICATION_DATE", lastModificationDate );
                this.putOnce("LAST_MODIFIED_BY", lastModifiedBy );
        }



	public File ( int fileID, String logicalFileName ) throws Exception  {       
                this.putOnce("FILE_ID", fileID );
                this.putOnce("LOGICAL_FILE_NAME", logicalFileName );
        }

	public void setFileID (int fileID) throws Exception {
 		this.put( "FILE_ID", fileID );
	}
	
	public void setLogicalFileName (String logicalFileName) throws Exception {
 		this.put( "LOGICAL_FILE_NAME", logicalFileName );
	}
	
	public void setIsFileValid (int isFileValid) throws Exception {
 		this.put( "IS_FILE_VALID", isFileValid );
	}
	
	public void setDatasetDO (Dataset datasetDO) throws Exception {
 		this.put( "DATASET_DO", datasetDO );
	}
	
	public void setBlockDO (Block blockDO) throws Exception {
 		this.put( "BLOCK_DO", blockDO );
	}
	
	public void setFileTypeDO (FileType fileTypeDO) throws Exception {
 		this.put( "FILE_TYPE_DO", fileTypeDO );
	}
	
	public void setCheckSum (String checkSum) throws Exception {
 		this.put( "CHECK_SUM", checkSum );
	}
	
	public void setEventCount (int eventCount) throws Exception {
 		this.put( "EVENT_COUNT", eventCount );
	}
	
	public void setFileSize (int fileSize) throws Exception {
 		this.put( "FILE_SIZE", fileSize );
	}
	
	public void setBranchHashDO (int branchHashDO) throws Exception {
 		this.put( "BRANCH_HASH_DO", branchHashDO );
	}
	
	public void setAdler32 (String adler32) throws Exception {
 		this.put( "ADLER32", adler32 );
	}
	
	public void setMd5 (String md5) throws Exception {
 		this.put( "MD5", md5 );
	}
	
	public void setAutoCrossSection (double autoCrossSection) throws Exception {
 		this.put( "AUTO_CROSS_SECTION", autoCrossSection );
	}
	
	public void setCreationDate (long creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setCreateBy (String createBy) throws Exception {
 		this.put( "CREATE_BY", createBy );
	}
	
	public void setLastModificationDate (long lastModificationDate) throws Exception {
 		this.put( "LAST_MODIFICATION_DATE", lastModificationDate );
	}
	
	public void setLastModifiedBy (String lastModifiedBy) throws Exception {
 		this.put( "LAST_MODIFIED_BY", lastModifiedBy );
	}
	
	public int getFileID ( )  throws Exception {
		int fileID = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_ID"))) {
                       	fileID = this.getInt("FILE_ID");
               	}
                return fileID;
        }
	
	public String getLogicalFileName ( )  throws Exception {
		String logicalFileName = null;
               	if (!JSONObject.NULL.equals(this.get("LOGICAL_FILE_NAME"))) {
                       	logicalFileName = this.getString("LOGICAL_FILE_NAME");
               	}
                return logicalFileName;
        }
	
	public int getIsFileValid ( )  throws Exception {
		int isFileValid = -1;
               	if (!JSONObject.NULL.equals(this.get("IS_FILE_VALID"))) {
                       	isFileValid = this.getInt("IS_FILE_VALID");
               	}
                return isFileValid;
        }
	
	public Dataset getDatasetDO ( )  throws Exception {
		Dataset datasetDO = null;
               	if (!JSONObject.NULL.equals(this.get("DATASET_DO"))) {
                       	datasetDO = (Dataset)this.getJSONObject("DATASET_DO");
               	}
                return datasetDO;
        }
	
	public Block getBlockDO ( )  throws Exception {
		Block block = null;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_DO"))) {
                       	block = (Block)this.getJSONObject("BLOCK_DO");
               	}
                return block;
        }
	
	public FileType getFileTypeDO ( )  throws Exception {
		FileType fileType = null;
               	if (!JSONObject.NULL.equals(this.get("FILE_TYPE_DO"))) {
                       	fileType = (FileType)this.getJSONObject("FILE_TYPE_DO");
               	}
                return fileType;
        }
	
	public String getCheckSum ( )  throws Exception {
		String checkSum = null;
               	if (!JSONObject.NULL.equals(this.get("CHECK_SUM"))) {
                       	checkSum = this.getString("CHECK_SUM");
               	}
                return checkSum;
        }
	
	public int getEventCount ( )  throws Exception {
		int eventCount = -1;
               	if (!JSONObject.NULL.equals(this.get("EVENT_COUNT"))) {
                       	eventCount = this.getInt("EVENT_COUNT");
               	}
                return eventCount;
        }
	
	public int getFileSize ( )  throws Exception {
		int fileSize = -1;
               	if (!JSONObject.NULL.equals(this.get("FILE_SIZE"))) {
                       	fileSize = this.getInt("FILE_SIZE");
               	}
                return fileSize;
        }
	
	public BranchHash getBranchHashDO ( )  throws Exception {
		 BranchHash branchHash = null;
               	if (!JSONObject.NULL.equals(this.get("BRANCH_HASH_DO"))) {
                       	branchHash = (BranchHash)this.getJSONObject("BRANCH_HASH_DO");
               	}
                return branchHash;
        }
	
	public String getAdler32 ( )  throws Exception {
		String adler32 = null;
               	if (!JSONObject.NULL.equals(this.get("ADLER32"))) {
                       	adler32 = this.getString("ADLER32");
               	}
                return adler32;
        }
	
	public String getMd5 ( )  throws Exception {
		String md5 = null;
               	if (!JSONObject.NULL.equals(this.get("MD5"))) {
                       	md5 = this.getString("MD5");
               	}
                return md5;
        }
	
	public double getAutoCrossSection ( )  throws Exception {
		double autoCrossSection = 0;
               	if (!JSONObject.NULL.equals(this.get("AUTO_CROSS_SECTION"))) {
                       	autoCrossSection = this.getDouble("AUTO_CROSS_SECTION");
               	}
                return autoCrossSection;
        }
	
	public long getCreationDate ( )  throws Exception {
		long creationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("CREATION_DATE"))) {
                       	creationDate = this.getLong("CREATION_DATE");
               	}
                return creationDate;
        }
	
	public String getCreateBy ( )  throws Exception {
		String createBy = null;
               	if (!JSONObject.NULL.equals(this.get("CREATE_BY"))) {
                       	createBy = this.getString("CREATE_BY");
               	}
                return createBy;
        }
	
	public long getLastModificationDate ( )  throws Exception {
		long lastModificationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("LAST_MODIFICATION_DATE"))) {
                       	lastModificationDate = this.getLong("LAST_MODIFICATION_DATE");
               	}
                return lastModificationDate;
        }
	
	public String getLastModifiedBy ( )  throws Exception {
		String lastModifiedBy = null;
               	if (!JSONObject.NULL.equals(this.get("LAST_MODIFIED_BY"))) {
                       	lastModifiedBy = this.getString("LAST_MODIFIED_BY");
               	}
                return lastModifiedBy;
        }
	
}
