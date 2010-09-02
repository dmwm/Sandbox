/***
 * $Id: DataTierQO.java,v 1.2 2009/10/13 16:05:30 yuyi Exp $
 *
 * This is the class for data tier query objects.
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
import cms.dbs.dataobjs.DataTier;

public class DataTierQO extends  DBSSimpleQueryObject{

    public DataTierQO() throws Exception{
            super();
    }
    //insert a Data Tier dataset into DB
    /*
    public PrimaryDataset putPrimaryDataset(Connection conn, PrimaryDataset cond1) throws Exception{
        //PrimaryDataset pd = null;
	PrimaryDSType PType = listPrimaryDSType(conn, (cond1.getPrimaryDSTypeDO()).getPrimaryDSType());
	int PTId = PType.getPrimaryDSTypeID();
	String PName = cond1.getPrimaryDSName();
	String sql = "insert into " + schemaOwner + "PRIMARY_DATASETS(PRIMARY_DS_NAME, PRIMARY_DS_TYPE_ID, PRIMARY_DS_ID)"
		    + "values(?,?,?)";
	PreparedStatement ps = null;
        try{
	    int PId = SequenceManager.getSequence(conn, "seq_PDS");
	    ps = DBManagement.getStatement(conn, sql);
	    if(PName != null && !PName.equals("") && PName.indexOf('_') == -1 
		&& PName.indexOf('%') == -1)ps.setString(1, PName);
	    else throw new DBSException("Input Data Error", "Primary Dataset Name " + PName + "is invalid");
	    ps.setInt(2, PTId);
	    ps.setInt(3, PId);
	    ps.execute();
	    cond1.setPrimaryDSID(PId);
	    cond1.setPrimaryDSTypeDO(PType);
	    return cond1;
	    //pd = new PrimaryDataset(PId, new PrimaryDSType( PTId, PType.getPrimaryDSType()), 
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
        //list only the data tier that satisfied the condition.
    public JSONArray listDataTier(Connection conn, DataTier cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT P.DATA_TIER_ID, P.DATA_TIER_NAME, P.CREATION_DATE, P.CREATE_BY FROM " +
            schemaOwner + "DATA_TIERS P " + " WHERE ";
	if ((cond.getDataTierName()).indexOf('_') != -1 || (cond.getDataTierName()).indexOf('%') != -1)
	    sql += " P.DATA_TIER_NAME like ?";
	else  sql += " P.DATA_TIER_NAME = ?";
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, cond.getDataTierName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("DATA_TIER_NAME");
                int pID = rs.getInt("DATA_TIER_ID");
                int dt = rs.getInt("CREATION_DATE");
                String by = rs.getString("CREATE_BY");
                this.result.put(new DataTier(pID, name,dt,by));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
}
