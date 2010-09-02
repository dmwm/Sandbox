/***
 * $Id: FileQO.java,v 1.8 2009/11/30 22:09:02 afaq Exp $
 *
 * This is the class for File query objects.
 * @author Y. Guo
 ***/
package cms.dbs.queryobjs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;
import cms.dbs.commons.db.DBManagement;
import cms.dbs.commons.db.SequenceManager;
import cms.dbs.commons.exceptions.DBSException;
import cms.dbs.commons.utils.DBSSrvcUtil;
import cms.dbs.dataobjs.PrimaryDataset;
import cms.dbs.dataobjs.ProcessedDataset;
import cms.dbs.dataobjs.DataTier;
import cms.dbs.dataobjs.DatasetType;
import cms.dbs.dataobjs.PhysicsGroup;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.dataobjs.File;
import cms.dbs.dataobjs.BranchHash;
import cms.dbs.dataobjs.FileType;
import cms.dbs.dataobjs.Block;
import cms.dbs.dataobjs.DataTier;

public class FileQO extends  DBSSimpleQueryObject{

    public FileQO() throws Exception{
            super();
    }
    //insert a dataset into DB
    public void putFile(Connection conn, File cond) throws Exception{
	//System.out.println(cond);
	//Check for LFN
	String LFN = cond.getLogicalFileName ( );
	if(LFN == null || LFN=="") throw new DBSException("Input Data Error", "LogicalFileName is expected.");
	//Check is_file_valid
	int fileValid = cond.getIsFileValid( );
        if(fileValid == -1) throw new DBSException("Input Data Error", "Validation of File is expected.");
	//Check if Datset already in db
	Dataset ds = cond.getDatasetDO();
	//System.out.println(ds);
	if(ds == null)throw new DBSException("Input Data Error", "Dataset is expected.");
	if(ds.getDatasetID( ) == 0){
	    String dsName = ds.getDataset();
	    if((dsName == null) || (dsName==""))throw new DBSException("Input Data Error", "Dataset name is missing");
	    JSONArray dss = (new DatasetQO()).listDatasets(conn,  ds);
	    if(dss.length() != 1)
		throw new DBSException("Input Data Error", "dataset name :" + dsName 
		+" is not found or more than one found in the db.");
	    else{ 
		ds.setDatasetID(((Dataset)dss.getJSONObject(0)).getDatasetID());
		//ds.setDataset(((Dataset)dss.getJSONObject(0)).getDataset());
	    }
	}
        //System.out.println(cond);
	Block bk  = cond.getBlockDO();
	if(bk == null) throw new DBSException("Input Data Error", "Block is expected.");
	if(bk.getBlockID() == 0){
	    String bkName = bk.getBlockName();
	    if(bkName == null || bkName == "")throw new DBSException("Input Data Error", "Block name is missing");
	    JSONArray bks = (new BlockQO()).listBlocks(conn,  bk);
	    if(bks.length() != 1 )
		throw new DBSException("Input Data Error", "More than one or no Blocks  are found in the db with name: "
		 + bkName);
	    else {
		bk.setBlockID(((Block)bks.getJSONObject(0)).getBlockID());
		//System.out.println("Block : " + bk);
	    }
	}
	//
	//System.out.println("Check for file_type");
	FileType ft = cond.getFileTypeDO();
        if(ft == null)throw new DBSException("Input Data Error", "File type is expected.");
        if(ft.getFileTypeID( ) == 0){
            String ftName = ft.getFileType();
            if((ftName == null) || (ftName == ""))throw new DBSException("Input Data Error", "File type is missing");
            JSONArray fts = (new FileTypeQO()).listFileTypes(conn,  ft);
            if(fts.length() != 1)
                throw new DBSException("Input Data Error", "File type :" + ftName
                +" is not found or more than one found in the db.");
            else
                ft.setFileTypeID(((FileType)fts.getJSONObject(0)).getFileTypeID());
        }
	//System.out.println("File type: " + ft);
	//check for Primary key
	int fileID = cond.getFileID ( );
	if(fileID == 0){
	    try{
		fileID = SequenceManager.getSequence(conn, "SEQ_FL");
		cond. setFileID(fileID);
	    }catch (SQLException ex) {
		throw ex;
	    }
	}
	//
	//System.out.println("Check for check-sum");
	String cs = cond.getCheckSum();
        if(cs == null || cs == "")throw new DBSException("Input Data Error", "File check-sum is expected.");
	//check for event_count
	if (cond.getEventCount() == -1) throw new DBSException("Input Data Error", "File event count is expected.");
	//check for file size
	if(cond.getFileSize() == -1) throw new DBSException("Input Data Error", "File size is expected.");
	//System.out.println("Check for creation_date and created_by. \n");
	long createDate = cond.getCreationDate( );
	String createdBy = cond.getCreateBy( );
	//System.out.println("****File**** " + cond);
	if(createDate == 0)cond.setCreationDate(DBSSrvcUtil.getEpoch());
        if(createdBy == null || createdBy=="")cond.setCreateBy("WeNeed2FindWhoDidIt");
	 	
	//Now we are ready to insert into the dataset
	//System.out.println("Ready to insert file :" + cond);
	insertTable(conn, cond, "FILES");
   }

    //list only the Files that satisfied the condition.
    //what is the reasonable data we should return to user? Whole datset object or just name and ID? 
    public JSONArray listFiles(Connection conn, File cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean fileID = false;
        String sql = "SELECT F.FILE_ID, F.LOGICAL_FILE_NAME LFN, F.IS_FILE_VALID, F.DATASET_ID, F.BLOCK_ID, F.FILE_TYPE_ID, "
		     +"  F.CHECK_SUM, F.EVENT_COUNT, F.FILE_SIZE,  F.BRANCH_HASH_ID, F.ADLER32, F.MD5, F.AUTO_CROSS_SECTION,"
		     + "  F.CREATION_DATE, F.CREATE_BY, F.LAST_MODIFICATION_DATE, F.LAST_MODIFIED_BY, "
		     + " B.BLOCK_NAME, D.DATASET , FT.FILE_TYPE"
                     + " FROM " + schemaOwner + "FILES F "
		     + " JOIN " + schemaOwner + "FILE_TYPES FT ON  FT.FILE_TYPE_ID = F.FILE_TYPE_ID "
                     + " JOIN " + schemaOwner + "DATASETS D ON  D.DATASET_ID = F.DATASET_ID "
		     + " JOIN " + schemaOwner + "BLOCKS B ON B.BLOCK_ID = F.BLOCK_ID"
                     + " WHERE ";
	if(cond.getFileID() != 0){ 
	    sql += "F.FILE_ID = ? ";
	    fileID =true;
	}

        else if ( cond.has("LOGICAL_FILE_NAME")) { 
		if (cond.getLogicalFileName() != null || cond.getLogicalFileName() != ""){
	    		if (  (cond.getLogicalFileName()).indexOf('%') != -1) sql += " F.LOGICAL_FILE_NAME like ?";
	    		else sql += " F.LOGICAL_FILE_NAME = ?";
		}
	}
	else if ( cond.has("DATASET_DO")) { 
		if( cond.getDatasetDO().getDataset() != null || cond.getDatasetDO().getDataset() != "") {
            		if (  (cond.getDatasetDO().getDataset() ).indexOf('%') != -1) sql += " D.DATASET like ?";
            		else sql += " D.DATASET = ?";
        	}
	}
	else if (  cond.has("BLOCK_DO")) {
		if ( cond.getBlockDO().getBlockName() != null || cond.getBlockDO().getBlockName() != "") {
			if (  (cond.getBlockDO().getBlockName()).indexOf('%') != -1) sql += " B.BLOCK_NAME like ?";
			else sql += " B.BLOCK_NAME = ?";
		}
	}
        else throw  new DBSException("Input Data Error", "File name (LFN) or ID , or BLOCK Name, or DATASET have to be provided. ");

        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    if(fileID)ps.setInt(1, cond.getFileID());
	    else if ( cond.has("LOGICAL_FILE_NAME")) ps.setString(1, cond.getLogicalFileName());
	    else if ( cond.has("DATASET_DO")) ps.setString(1, cond.getDatasetDO().getDataset());
	    else if ( cond.has("BLOCK_DO")) ps.setString(1, cond.getBlockDO().getBlockName());
	    //else ps.setString(1, cond.getLogicalFileName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String lfn = rs.getString("LFN");
                int fileId = rs.getInt("FILE_ID");
		int isValid = rs.getInt("IS_FILE_VALID");
		Dataset ds = new Dataset(rs.getInt("DATASET_ID"), rs.getString("DATASET"));
		Block bk = new Block(rs.getInt("BLOCK_ID"), rs.getString("BLOCK_NAME"));
		FileType ftype = new FileType(rs.getInt("FILE_TYPE_ID"), rs.getString("FILE_TYPE"));
		String cksum = rs.getString("CHECK_SUM");
		int ecnt = rs.getInt("EVENT_COUNT");
		int fsize =  rs.getInt("FILE_SIZE");
		BranchHash bh = new BranchHash (rs.getInt("BRANCH_HASH_ID"));
		double xcr = rs.getDouble("AUTO_CROSS_SECTION");
		String adl = rs.getString("ADLER32");
		String md = rs.getString("MD5");
		long cDate = rs.getLong("CREATION_DATE");
		String cBy =  rs.getString("CREATE_BY");
		long lDate = rs.getLong("LAST_MODIFICATION_DATE");
		String lBy = rs.getString("LAST_MODIFIED_BY");
                
		this.result.put(new File(fileId, lfn, isValid,  ds, bk, ftype, cksum, ecnt, fsize, bh, 
                                adl, md, xcr, cDate, cBy, lDate, lBy));
	    }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
    public JSONArray listFiles(Connection conn, JSONArray cond) throws Exception{
        this.result = new JSONArray();
        int condSize = 0;
        condSize = cond.length();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT F.FILE_ID, F.LOGICAL_FILE_NAME LFN, F.IS_FILE_VALID, F.DATASET_ID, F.BLOCK_ID, F.FILE_TYPE_ID, "
                     +"  F.CHECK_SUM, F.EVENT_COUNT, F.FILE_SIZE,  F.BRANCH_HASH_ID, F.ADLER32, F.MD5, F.AUTO_CROSS_SECTION,"
                     + "  F.CREATION_DATE, F.CREATE_BY, F.LAST_MODIFICATION_DATE, F.LAST_MODIFIED_BY, "
                     + " B.BLOCK_NAME, D.DATASET , FT.FILE_TYPE"
                     + " FROM " + schemaOwner + "FILES F "
                     + " JOIN " + schemaOwner + "FILE_TYPES FT ON  FT.FILE_TYPE_ID = F.FILE_TYPE_ID "
                     + " JOIN " + schemaOwner + "DATASETS D ON  D.DATASET_ID = F.DATASET_ID "
                     + " JOIN " + schemaOwner + "BLOCKS B ON B.BLOCK_ID = F.BLOCK_ID"
                     + " WHERE ";
            
        for (int i=0; i<condSize; i++){
            File f = (File)cond.getJSONObject(i);
            if (i==0){
                if ( (f.getLogicalFileName()).indexOf('%') != -1)
                	sql += " F.LOGICAL_FILE_NAME like ?";
                	else  sql += " F.LOGICAL_FILE_NAME = ?";
		}
            else{
                if ( (f.getLogicalFileName()).indexOf('%') != -1)
                sql +=  " or  F.LOGICAL_FILE_NAME like ?";
                else sql +=  "or  F.LOGICAL_FILE_NAME  = ?";
            }
        }
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
            for (int i=1; i<=condSize; i++){
                File f = (File)cond.getJSONObject(i-1);
                ps.setString(i, f.getLogicalFileName());
            }
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
		String lfn = rs.getString("LFN");
                int fileId = rs.getInt("FILE_ID");
                int isValid = rs.getInt("IS_FILE_VALID");
                Dataset ds = new Dataset(rs.getInt("DATASET_ID"), rs.getString("DATASET"));
                Block bk = new Block(rs.getInt("BLOCK_ID"), rs.getString("BLOCK_NAME"));
                FileType ftype = new FileType(rs.getInt("FILE_TYPE_ID"), rs.getString("FILE_TYPE"));
                String cksum = rs.getString("CHECK_SUM");
                int ecnt = rs.getInt("EVENT_COUNT");
                int fsize =  rs.getInt("FILE_SIZE");
                BranchHash bh = new BranchHash (rs.getInt("BRANCH_HASH_ID"));
                double xcr = rs.getDouble("AUTO_CROSS_SECTION");
                String adl = rs.getString("ADLER32");
                String md = rs.getString("MD5");
                long cDate = rs.getLong("CREATION_DATE");
                String cBy =  rs.getString("CREATE_BY");
                long lDate = rs.getLong("LAST_MODIFICATION_DATE");
                String lBy = rs.getString("LAST_MODIFIED_BY");

                this.result.put(new File(fileId, lfn, isValid,  ds, bk, ftype, cksum, ecnt, fsize, bh,
                                adl, md, xcr, cDate, cBy, lDate, lBy));	
	    }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
    //list only the bared Files that do not contain Block DOs, dataset DOs and so on.
    public JSONArray listBaredFiles(Connection conn, File cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean fileID = false;
        String sql = "SELECT F.FILE_ID, F.LOGICAL_FILE_NAME LFN, F.IS_FILE_VALID, F.DATASET_ID, F.BLOCK_ID, F.FILE_TYPE_ID, "
		     +"  F.CHECK_SUM, F.EVENT_COUNT, F.FILE_SIZE,  F.BRANCH_HASH_ID, F.ADLER32, F.MD5, F.AUTO_CROSS_SECTION,"
		     + "  F.CREATION_DATE, F.CREATE_BY, F.LAST_MODIFICATION_DATE, F.LAST_MODIFIED_BY "
                     + " FROM " + schemaOwner + "FILES F "
                     + " WHERE ";
	if(cond.getFileID() != 0){ 
	    sql += "F.FILE_ID = ? ";
	    fileID =true;
	}
        else if (cond.getLogicalFileName() != null || cond.getLogicalFileName() != ""){
	    if (  (cond.getLogicalFileName()).indexOf('%') != -1) sql += " F.LOGICAL_FILE_NAME like ?";
	    else sql += " F.LOGICAL_FILE_NAME = ?";
	}
        else throw  new DBSException("Input Data Error", "File name (LFN) or ID have to be provided. ");

        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    if(fileID)ps.setInt(1, cond.getFileID());
	    else ps.setString(1, cond.getLogicalFileName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String lfn = rs.getString("LFN");
                int fileId = rs.getInt("FILE_ID");
		int isValid = rs.getInt("IS_FILE_VALID");
		String cksum = rs.getString("CHECK_SUM");
		int ecnt = rs.getInt("EVENT_COUNT");
		int fsize =  rs.getInt("FILE_SIZE");
		double xcr = rs.getDouble("AUTO_CROSS_SECTION");
		String adl = rs.getString("ADLER32");
		String md = rs.getString("MD5");
		long cDate = rs.getLong("CREATION_DATE");
		String cBy =  rs.getString("CREATE_BY");
		long lDate = rs.getLong("LAST_MODIFICATION_DATE");
		String lBy = rs.getString("LAST_MODIFIED_BY");
                
		this.result.put(new File(fileId, lfn, isValid, cksum, ecnt, fsize,
                                adl, md, xcr, cDate, cBy, lDate, lBy));
	    }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
    public JSONArray listBaredFiles(Connection conn, JSONArray cond) throws Exception{
        this.result = new JSONArray();
        int condSize = 0;
        condSize = cond.length();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT F.FILE_ID, F.LOGICAL_FILE_NAME LFN, F.IS_FILE_VALID, F.DATASET_ID, F.BLOCK_ID, F.FILE_TYPE_ID, "
                     +"  F.CHECK_SUM, F.EVENT_COUNT, F.FILE_SIZE,  F.BRANCH_HASH_ID, F.ADLER32, F.MD5, F.AUTO_CROSS_SECTION,"
                     + "  F.CREATION_DATE, F.CREATE_BY, F.LAST_MODIFICATION_DATE, F.LAST_MODIFIED_BY "
                     + " FROM " + schemaOwner + "FILES F "
                     + " WHERE ";
            
        for (int i=0; i<condSize; i++){
            File f = (File)cond.getJSONObject(i);
            if (i==0){
                if ( (f.getLogicalFileName()).indexOf('%') != -1)
                sql += " F.LOGICAL_FILE_NAME like ?";
                else  sql += " F.LOGICAL_FILE_NAME = ?";
            }
            else{
                if ( (f.getLogicalFileName()).indexOf('%') != -1)
                sql +=  " or  F.LOGICAL_FILE_NAME like ?";
                else sql +=  "or  F.LOGICAL_FILE_NAME  = ?";
            }
        }
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
            for (int i=1; i<=condSize; i++){
                File f = (File)cond.getJSONObject(i-1);
                ps.setString(i, f.getLogicalFileName());
            }
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
		String lfn = rs.getString("LFN");
                int fileId = rs.getInt("FILE_ID");
                int isValid = rs.getInt("IS_FILE_VALID");
                String cksum = rs.getString("CHECK_SUM");
                int ecnt = rs.getInt("EVENT_COUNT");
                int fsize =  rs.getInt("FILE_SIZE");
                double xcr = rs.getDouble("AUTO_CROSS_SECTION");
                String adl = rs.getString("ADLER32");
                String md = rs.getString("MD5");
                long cDate = rs.getLong("CREATION_DATE");
                String cBy =  rs.getString("CREATE_BY");
                long lDate = rs.getLong("LAST_MODIFICATION_DATE");
                String lBy = rs.getString("LAST_MODIFIED_BY");

                this.result.put(new File(fileId, lfn, isValid, cksum, ecnt, fsize,
                                adl, md, xcr, cDate, cBy, lDate, lBy));	
	    }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }

}
