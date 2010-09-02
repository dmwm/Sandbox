/***
 * $Id: BlockQO.java,v 1.5 2009/11/30 22:09:01 afaq Exp $
 *
 * This is the class for Block query objects.
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
import cms.dbs.dataobjs.Block;
import cms.dbs.dataobjs.Site;

public class BlockQO extends  DBSSimpleQueryObject{

    public BlockQO() throws Exception{
            super();
    }
    //insert a Block into DB. Will do it later.
    
    public void putBlock(Connection conn, Block cond) throws Exception{
	//System.out.println(cond);
	//Check for existence of dataset and get dataset ID
	String block = cond.getBlockName ( );
	if(block == null || block == "") throw new DBSException("Input Data Error", "block name is expected.");
	String[] get_path = block.split("#");
	String path=get_path[0];
	//System.out.println("PATH:::::::::"+path);
	//Check if Datset already in db
	Dataset ds = new Dataset(0, path);
	//System.out.println(ds);
	if(ds == null) throw new DBSException("Input Data Error", "Dataset is expected.");
	if(ds.getDatasetID( ) == 0){
	    String dsName = ds.getDataset();
	    if(dsName == null || dsName=="")throw new DBSException("Input Data Error", "Dataset name is missing");
	    JSONArray dss = (new DatasetQO()).listDatasets(conn,  ds);
	    if(dss.length() != 1)
		throw new DBSException("Input Data Error", "dataset name :" + dsName 
		+" is not found or more than one found in the db.");
	    else{ 
		ds.setDatasetID(((Dataset)dss.getJSONObject(0)).getDatasetID());
		//ds.setDataset(((Dataset)dss.getJSONObject(0)).getDataset());
	    }
	}

	//Set this dataset as the dataset for this block
	cond.setDatasetID(ds);

	//check for Primary key
        int blockID = cond.getBlockID ( );
        if(blockID == 0){
            try{
                blockID = SequenceManager.getSequence(conn, "SEQ_BK");
                cond. setBlockID(blockID);
            }catch (SQLException ex) {
                throw ex;
            }
        }
 
        long createDate = cond.getCreationDate( );
        String createdBy = cond.getCreateBy( );
        if(createDate == 0) cond.setCreationDate((int)DBSSrvcUtil.getEpoch());
        if(createdBy == null || createdBy=="")cond.setCreateBy("WeNeed2FindWhoDidIt");
	
	//Now we are ready to insert into the dataset
	//System.out.println(cond);
	insertTable(conn, cond, "BLOCKS");
   }
    //list only the Blocks that satisfied the condition.
    public JSONArray listBlocks(Connection conn, Block cond) throws Exception{
        JSONArray myResult = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean blockID = false;
        String sql = "SELECT B.BLOCK_ID, B.BLOCK_NAME, B.DATASET_ID, B.OPEN_FOR_WRITING, B.ORIGIN_SITE, B.BLOCK_SIZE, "
		     + "  B.FILE_COUNT, B.CREATION_DATE, B.CREATE_BY, B.LAST_MODIFICATION_DATE, B.LAST_MODIFIED_BY, "
		     + " SI.SITE_NAME, DS.DATASET "
                     + " FROM " + schemaOwner + "BLOCKS B " 
                     + " JOIN " + schemaOwner + "DATASETS DS ON  DS.DATASET_ID = B.DATASET_ID "
		     + " LEFT OUTER JOIN " + schemaOwner + "SITES SI on SI.SITE_ID = B.ORIGIN_SITE"
                     + " WHERE ";
	if(cond.getBlockID() != 0){ 
	    sql += "B.BLOCK_ID = ? ";
	    blockID =true;
	}
        else if (cond.getBlockName() != null || cond.getBlockName() != ""){
	    if ( (cond.getBlockName()).indexOf('%') != -1) sql += "B.BLOCK_NAME like ?";
	    else sql += "B.BLOCK_NAME = ?";
	}
        else throw  new DBSException("Input Data Error", "Block name or ID have to be provided. ");

        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    if(blockID)ps.setInt(1, cond.getBlockID());
	    else ps.setString(1, cond.getBlockName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String bkName = rs.getString("BLOCK_NAME");
                int bkID = rs.getInt("BLOCK_ID");
		int open = rs.getInt("OPEN_FOR_WRITING");
		//System.out.println("\n***Original site id: " + rs.getInt("ORIGIN_SITE"));
		//System.out.println("\n***Original site Name: " + rs.getString("SITE_NAME"));
		Site org = new Site(rs.getInt("ORIGIN_SITE"), rs.getString("SITE_NAME"));
		//System.out.println("\n***Block size: " + rs.getLong("BLOCK_SIZE"));
		long blockSite = rs.getLong("BLOCK_SIZE");
		//System.out.println("\n***FILE_COUNT " + rs.getLong("FILE_COUNT"));
		long fileCnt =  rs.getLong("FILE_COUNT");
		long cDate = rs.getLong("CREATION_DATE");
		String cBy =  rs.getString("CREATE_BY");
		long lDate = rs.getLong("LAST_MODIFICATION_DATE");
		String lBy = rs.getString("LAST_MODIFIED_BY");
		Dataset ds = new Dataset( rs.getInt("DATASET_ID"), rs.getString("DATASET"));
		myResult.put(new Block(bkID, bkName, ds, open, org, blockSite, fileCnt, cDate, cBy, lDate, lBy ));	
	    }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
	    //System.out.println(myResult);
            return myResult;
    }

}
