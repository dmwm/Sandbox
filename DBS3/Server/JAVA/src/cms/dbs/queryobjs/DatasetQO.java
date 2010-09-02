/***
 * $Id: DatasetQO.java,v 1.6 2009/11/30 22:09:01 afaq Exp $
 *
 * This is the class for dataset query objects.
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

public class DatasetQO extends  DBSSimpleQueryObject{

    public DatasetQO() throws Exception{
            super();
    }
    //insert a dataset into DB
    public void putDataset(Connection conn, Dataset cond) throws Exception{
	//System.out.println(cond);
	//Check for dataset path
	String path = cond.getDataset();
	if(path == null) throw new DBSException("Input Data Error", "Dataset is expected.");
	//Check is_dataset_valid
	int DSvalid = cond.getIsDatasetValid( );
        if(DSvalid == -1) throw new DBSException("Input Data Error", "Validation of dataset is expected."); 
	//
	//System.out.println("require primary dataset in the Dataset to be inserted into db.\n"); 
	PrimaryDataset primaryDS = cond.getPrimaryDSDO();
	//System.out.println(primaryDS);
	if(primaryDS == null)throw new DBSException("Input Data Error", "Primary dataset is expected.");
	if(primaryDS.getPrimaryDSID( ) == 0){
	    String primaryDSName = primaryDS.getPrimaryDSName();
	    if(primaryDSName == null)throw new DBSException("Input Data Error", "Primary dataset name is missing");
	    JSONArray primaryDSs = (new PrimaryDatasetQO()).listPrimaryDatasets(conn,  primaryDS);
	    if(primaryDSs.length() != 1)
		throw new DBSException("Input Data Error", "Primarydataset name :" + primaryDSName 
		+" is not found or more than one found in the db.");
	    else{ 
		//primaryDS = (PrimaryDataset)primaryDSs.getJSONObject(0);
		//Do we really need to set everything?
		primaryDS.setPrimaryDSID(((PrimaryDataset)primaryDSs.getJSONObject(0)).getPrimaryDSID());
		primaryDS.setPrimaryDSName(((PrimaryDataset)primaryDSs.getJSONObject(0)).getPrimaryDSName());
		//primaryDS.setPrimaryDSTypeDO(((PrimaryDataset)primaryDSs.getJSONObject(0)).getPrimaryDSTypeDO());
		//primaryDS.setCreationDate(((PrimaryDataset)primaryDSs.getJSONObject(0)).getCreationDate());
		//primaryDS.setCreateBy(((PrimaryDataset)primaryDSs.getJSONObject(0)).getCreateBy());
	    }
	}
        //System.out.println(cond);
	//System.out.println(primaryDS);
	//System.out.println("check if the processed dataset already in the db, if not insert it\n");
	ProcessedDataset processedDS = cond.getProcessedDSDO();
	if(processedDS == null) throw new DBSException("Input Data Error", "Processed dataset is expected.");
	if(processedDS.getProcessedDSID() == 0){
	    String processedDSName = processedDS.getProcessedDSName();
	    if(processedDSName == null)throw new DBSException("Input Data Error", "Processed dataset name is missing");
	    JSONArray processedDSs = (new ProcessedDatasetQO()).listProcessedDatasets(conn,  processedDS);
	    if(processedDSs.length() >1 )
		throw new DBSException("Input Data Error", "More than one Processed dataset are found in the db with name: "
		 + processedDSName);
	    else if(processedDSs.length() == 1){
		processedDS.setProcessedDSID(((ProcessedDataset)processedDSs.getJSONObject(0)).getProcessedDSID());
		//System.out.println("Processed DS: " + processedDS);
	    }
	    else{
	    //not found in the db, now we insert it
	     (new ProcessedDatasetQO()).putProcessedDataset(conn, processedDS);
	     //System.out.println("inserted processed DS: " + processedDS); 
	    }
	}
	//
	//System.out.println("Check for data_tier");
	DataTier dataTier = cond.getDataTierDO();
        if(dataTier== null)throw new DBSException("Input Data Error", "Data Tier is expected.");
        if(dataTier.getDataTierID( ) == 0){
            String dataTierName = dataTier.getDataTierName();
            if(dataTierName == null)throw new DBSException("Input Data Error", "Data tier name is missing");
            JSONArray dataTiers = (new DataTierQO()).listDataTier(conn,  dataTier);
            if(dataTiers.length() != 1)
                throw new DBSException("Input Data Error", "Data tier name :" + dataTierName
                +" is not found or more than one found in the db.");
            else
                dataTier.setDataTierID(((DataTier)dataTiers.getJSONObject(0)).getDataTierID());
        }
	//System.out.println("list data tier: " + dataTier);
	//last but not least, check for primary key
	int datasetID = cond.getDatasetID ( );
	if(datasetID == 0){
	    try{
		datasetID = SequenceManager.getSequence(conn, "SEQ_DS");
		cond. setDatasetID(datasetID);
	    }catch (SQLException ex) {
		throw ex;
	    }
	}
	//
	//System.out.println("Check dataset Type");
	DatasetType dataType = cond.getDatasetTypeDO();
        if(dataType== null)throw new DBSException("Input Data Error", "Dataset type is expected.");
        if(dataType.getDatasetTypeID( ) == 0){
            String dataTypeName = dataType.getDatasetType();
            if(dataTypeName == null)throw new DBSException("Input Data Error", "Dataset type is missing");
            JSONArray dataTypes = (new DatasetTypeQO()).listDatasetTypes(conn,  dataType);
            if(dataTypes.length() != 1)
                throw new DBSException("Input Data Error", "Dataset type :" + dataTypeName
                +" is not found or more than one found in the db.");
            else
                dataType.setDatasetTypeID(((DatasetType)dataTypes.getJSONObject(0)).getDatasetTypeID());
        }
        //System.out.println("list dataset Type: " + dataType);
	//
	//System.out.println("Check for creation_date and created_by. \n");
	long createDate = cond.getCreationDate( );
	String createdBy = cond.getCreateBy( );
	if(createDate == 0)cond.setCreationDate(DBSSrvcUtil.getEpoch());
        if(createdBy == null || createdBy=="")cond.setCreateBy("Someone created it");
	 	
	//Now we are ready to insert into the dataset
	//System.out.println(cond);
	insertTable(conn, cond, "DATASETS");
   }

    //list only the dataset that satisfied the condition.
    public JSONArray listDatasets(Connection conn, Dataset cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean datasetID = false;

//System.out.println("I am here...");

        String sql = "SELECT D.DATASET_ID, D.DATASET, D.IS_DATASET_VALID,D.PRIMARY_DS_ID, D.PROCESSED_DS_ID, D.DATA_TIER_ID, "
		     +" D.DATASET_TYPE_ID, D.ACQUISITION_ERA_ID, D.PROCESSING_ERA_ID, D.PHYSICS_GROUP_ID, D.XTCROSSSECTION, D.GLOBAL_TAG,"
		     + "  D.CREATION_DATE, D.CREATE_BY, D.LAST_MODIFICATION_DATE,D.LAST_MODIFIED_BY,"  
		     //+ "  PR.PRIMARY_DS_NAME, "
		     //+ "  DT.DATA_TIER_NAME, PS.PROCESSED_DS_NAME, "
		     + " PH.PHYSICS_GROUP_NAME, DP.DATASET_TYPE"
                     + " FROM " + schemaOwner + "DATASETS D " 
                     //+ " JOIN " + schemaOwner + "PRIMARY_DATASETS PR ON  PR.PRIMARY_DS_ID = D.PRIMARY_DS_ID "
		     //+ " JOIN " + schemaOwner + "DATA_TIERS DT ON DT.DATA_TIER_ID = D.DATA_TIER_ID"
		     //+ " JOIN " + schemaOwner + "PROCESSED_DATASETS PS ON PS.PROCESSED_DS_ID = D.PROCESSED_DS_ID "	  
		     + " LEFT OUTER JOIN " + schemaOwner + "PHYSICS_GROUPS PH ON PH.PHYSICS_GROUP_ID = D.PHYSICS_GROUP_ID"
		     + " JOIN " + schemaOwner + "DATASET_TYPES DP on DP.DATASET_TYPE_ID = D.DATASET_TYPE_ID"
                     + " WHERE ";
	if(cond.getDatasetID() != 0){ 
	    sql += "D.DATASET_ID = ? ";
	    datasetID =true;
	}
        else if (cond.getDataset() != null){
	    if (  (cond.getDataset()).indexOf('%') != -1) sql += "D.DATASET like ?";
	    else sql += "D.DATASET = ?";
	}
        else throw  new DBSException("Input Data Error", "Dataset name or ID have to be provided. ");

        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    if(datasetID)ps.setInt(1, cond.getDatasetID());
	    else ps.setString(1, cond.getDataset());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String dataset = rs.getString("DATASET");
                int setID = rs.getInt("DATASET_ID");
		int isDatasetValid = rs.getInt("IS_DATASET_VALID");
		int primaryDSID = rs.getInt("PRIMARY_DS_ID");
		int processedDSID =  rs.getInt("PROCESSED_DS_ID");
		int dataTierID =  rs.getInt("DATA_TIER_ID");
		int datasetTypeID = rs.getInt("DATASET_TYPE_ID");
		int phygrpID = rs.getInt("PHYSICS_GROUP_ID");
		double xtcr = rs.getDouble("XTCROSSSECTION");
		String gTag = rs.getString("GLOBAL_TAG");
		int cDate = rs.getInt("CREATION_DATE");
		String cBy =  rs.getString("CREATE_BY");
		int lDate = rs.getInt("LAST_MODIFICATION_DATE");
		String lBy = rs.getString("LAST_MODIFIED_BY");
		//String primaryDSName = rs.getString("PRIMARY_DS_NAME");
                //String dataTiername = rs.getString("DATA_TIER_NAME");
	        //String processedDSName = rs.getString("PROCESSED_DS_NAME");
		String phGrpName = rs.getString("PHYSICS_GROUP_NAME");
		String datasetType = rs.getString("DATASET_TYPE");
		//System.out.println("DATSET: "+ dataset );
		this.result.put(new Dataset(setID, dataset, isDatasetValid, new PrimaryDataset(primaryDSID,
		"primaryDSName" ), new ProcessedDataset(processedDSID, "processedDSName"), 
		 new DataTier(dataTierID, "dataTiername"),
		 new DatasetType(datasetTypeID, datasetType), null, null, new PhysicsGroup(phygrpID, phGrpName),
	         xtcr, gTag, cDate, cBy, lDate, lBy));	
	    }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }

}
