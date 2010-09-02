/***
 * $Id: ProcessedDatasetQO.java,v 1.4 2009/11/05 19:44:31 afaq Exp $
 *
 * This is the class for processed dataset query objects.
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
import cms.dbs.dataobjs.ProcessedDataset;

public class ProcessedDatasetQO extends  DBSSimpleQueryObject{

    public ProcessedDatasetQO() throws Exception{
            super();
    }
    //insert a processed dataset into DB
    public ProcessedDataset putProcessedDataset(Connection conn, ProcessedDataset cond) throws Exception{
	String PName = cond.getProcessedDSName();
	if(PName == null) return null;
	String sql = "insert into " + schemaOwner + "PROCESSED_DATASETS(PROCESSED_DS_NAME, PROCESSED_DS_ID)"
		    + "values(?,?)";
	//System.out.println(sql);
	PreparedStatement ps = null;
        try{
	    int PId = SequenceManager.getSequence(conn, "SEQ_PSDS");
	    ps = DBManagement.getStatement(conn, sql);
	    if(PName != null && !PName.equals("")  
		&& PName.indexOf('%') == -1)ps.setString(1, PName);
	    else throw new DBSException("Input Data Error", "Processed Dataset Name " + PName + "is invalid");
	    //System.out.println(ps.toString());
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

        //list only the processed dataset that satisfied the condition.
    public JSONArray listProcessedDatasets(Connection conn, ProcessedDataset cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT P.PROCESSED_DS_ID, P.PROCESSED_DS_NAME FROM " +
            schemaOwner + "PROCESSED_DATASETS P WHERE " ;
	if ((cond.getProcessedDSName()).indexOf('_') != -1 || (cond.getProcessedDSName()).indexOf('%') != -1)
	    sql += " P.PROCESSED_DS_NAME like ?";
	else  sql += " P.PROCESSED_DS_NAME = ?";
	//System.out.println("*****" + sql + "\n");
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, cond.getProcessedDSName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("PROCESSED_DS_NAME");
                int pID = rs.getInt("PROCESSED_DS_ID");
                this.result.put(new ProcessedDataset(pID, name));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
}
