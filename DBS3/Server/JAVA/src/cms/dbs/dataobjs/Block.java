/**
 * 
 $Revision: 1.8 $"
 $Id: Block.java,v 1.8 2009/10/22 15:29:08 yuyi Exp $"
 *
 * Data Object from table : BLOCKS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.dataobjs.Site;

public class Block extends JSONObject  {

	public Block ( ) {

	}
		
        public Block ( int blockID, String blockName, Dataset datasetDO, int openForWriting, Site originSiteDO, long blockSize, long
	fileCount, long creationDate, String createBy, long lastModificationDate, String lastModifiedBy ) throws Exception  {
		
                this.putOnce("BLOCK_ID", blockID );
                this.putOnce("BLOCK_NAME", blockName );
                this.putOnce("DATASET_DO", datasetDO );
                this.putOnce("OPEN_FOR_WRITING", openForWriting );
                this.putOnce("ORIGIN_SITE_DO", originSiteDO );
                this.putOnce("BLOCK_SIZE", blockSize );
                this.putOnce("FILE_COUNT", fileCount );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
                this.putOnce("LAST_MODIFICATION_DATE", lastModificationDate );
                this.putOnce("LAST_MODIFIED_BY", lastModifiedBy );
        }

	public Block ( int blockID, String blockName) throws Exception  {

                this.putOnce("BLOCK_ID", blockID );
                this.putOnce("BLOCK_NAME", blockName );
        }

	public void setBlockID (int blockID) throws Exception {
 		this.put( "BLOCK_ID", blockID );
	}
	
	public void setBlockName (String blockName) throws Exception {
 		this.put( "BLOCK_NAME", blockName );
	}
	
	public void setDatasetID (Dataset datasetDO) throws Exception {
 		this.put( "DATASET_DO", datasetDO );
	}
	
	public void setOpenForWriting (int openForWriting) throws Exception {
 		this.put( "OPEN_FOR_WRITING", openForWriting );
	}
	
	public void setOriginSite (Site originSite) throws Exception {
 		this.put( "ORIGIN_SITE_DO", originSite );
	}
	
	public void setBlockSize (int blockSize) throws Exception {
 		this.put( "BLOCK_SIZE", blockSize );
	}
	
	public void setFileCount (int fileCount) throws Exception {
 		this.put( "FILE_COUNT", fileCount );
	}
	
	public void setCreationDate (int creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setCreateBy (String createBy) throws Exception {
 		this.put( "CREATE_BY", createBy );
	}
	
	public void setLastModificationDate (int lastModificationDate) throws Exception {
 		this.put( "LAST_MODIFICATION_DATE", lastModificationDate );
	}
	
	public void setLastModifiedBy (String lastModifiedBy) throws Exception {
 		this.put( "LAST_MODIFIED_BY", lastModifiedBy );
	}
	
	public int getBlockID ( )  throws Exception {
		int blockID = 0;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_ID"))) {
                       	blockID = this.getInt("BLOCK_ID");
               	}
                return blockID;
        }
	
	public String getBlockName ( )  throws Exception {
		String blockName = null;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_NAME"))) {
                       	blockName = this.getString("BLOCK_NAME");
               	}
                return blockName;
        }
	
	public Dataset getDatasetDO ( )  throws Exception {
		Dataset datasetDO = null;
               	if (!JSONObject.NULL.equals(this.get("DATASET_DO"))) {
                       	datasetDO = (Dataset)this.getJSONObject("DATASET_DO");
               	}
                return datasetDO;
        }
	
	public int getOpenForWriting ( )  throws Exception {
		int openForWriting = 0;
               	if (!JSONObject.NULL.equals(this.get("OPEN_FOR_WRITING"))) {
                       	openForWriting = this.getInt("OPEN_FOR_WRITING");
               	}
                return openForWriting;
        }
	
	public Site getOriginSiteDO ( )  throws Exception {
		Site originSite = null;
               	if (!JSONObject.NULL.equals(this.get("ORIGIN_SITE_DO"))) {
                       	originSite = (Site)this.getJSONObject("ORIGIN_SITE_DO");
               	}
                return originSite;
        }
	
	public long getBlockSize ( )  throws Exception {
		long blockSize = 0;
               	if (!JSONObject.NULL.equals(this.get("BLOCK_SIZE"))) {
                       	blockSize = this.getLong("BLOCK_SIZE");
               	}
                return blockSize;
        }
	
	public long getFileCount ( )  throws Exception {
		long fileCount = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_COUNT"))) {
                       	fileCount = this.getLong("FILE_COUNT");
               	}
                return fileCount;
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

