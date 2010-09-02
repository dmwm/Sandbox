/***
 * $Id: DatasetTypeQO.java,v 1.3 2009/10/15 12:39:11 yuyi Exp $
 *
 * This is the class for dataset type query Object.
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
import cms.dbs.dataobjs.DatasetType;

public class DatasetTypeQO extends  DBSSimpleQueryObject{

    public DatasetTypeQO() throws Exception{
            super();
    }
    /* Will do this later, assume all the type are inserted at schema start-up time.
    public ProcessedDataset putProcessedDataset(Connection conn, ProcessedDataset cond) throws Exception{
	String PName = cond.getProcessedDSName();
	if(PName == null) return null
	String sql = "insert into " + schemaOwner + "PROCESSED_DATASETS(PROCESSED_DS_NAME, PROCESSED_DS_ID)"
		    + "values(?,?)";
	PreparedStatement ps = null;
        try{
	    int PId = SequenceManager.getSequence(conn, "SEQ_PSDS");
	    ps = DBManagement.getStatement(conn, sql);
	    if(PName != null && !PName.equals("") && PName.indexOf('_') == -1 
		&& PName.indexOf('%') == -1)ps.setString(1, PName);
	    else throw new DBSException("Input Data Error", "Processed Dataset Name " + PName + "is invalid");
	    ps.setInt(2, PId);
	    ps.execute();
	    cond.setProcessedDSID(PId);
	    return cond;
	}catch (SQLException ex) {
	    String exmsg = ex.getMessage();
	    if(!exmsg.startsWith("Duplicate entry") && !exmsg.startsWith("ORA-00001: unique constraint") ) throw ex;
	    else DBSSrvcUtil.writeLog( PName + " Already Exists");
	} finally {
	    if (ps != null) ps.close();
	}
	return null;
   }
*/
        //list only the dataset type that satisfied the condition.
    public JSONArray listDatasetTypes(Connection conn, DatasetType cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT P.DATASET_TYPE_ID, P.DATASET_TYPE FROM " +
            schemaOwner + " DATASET_TYPES P  WHERE" ;
	if ((cond.getDatasetType()).indexOf('_') != -1 || (cond.getDatasetType()).indexOf('%') != -1)
	    sql += " P.DATASET_TYPE like ?";
	else  sql += " P.DATASET_TYPE = ?";
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, cond.getDatasetType());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("DATASET_TYPE");
                int pID = rs.getInt("DATASET_TYPE_ID");
                this.result.put(new DatasetType(pID, name));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
}
