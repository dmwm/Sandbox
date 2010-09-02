/***
 * $Id: PrimaryDatasetQO.java,v 1.8 2009/11/05 19:44:31 afaq Exp $
 *
 * This is the class for primary dataset query objects.
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
import cms.dbs.dataobjs.PrimaryDSType;

public class PrimaryDatasetQO extends  DBSSimpleQueryObject{

    public PrimaryDatasetQO() throws Exception{
            super();
    }
    //insert a primary dataset into DB
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
	    if(PName != null && !PName.equals("") 
			//&& PName.indexOf('_') == -1 
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

        //list only the primary dataset that satisfied the condition.
    public JSONArray listPrimaryDatasets(Connection conn, PrimaryDataset cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT P.PRIMARY_DS_ID, P.PRIMARY_DS_NAME, P.PRIMARY_DS_TYPE_ID, PT.PRIMARY_DS_TYPE, " +
            "P.CREATION_DATE, P.CREATE_BY FROM " +
            schemaOwner + "PRIMARY_DATASETS P " +
            "JOIN " + schemaOwner + "PRIMARY_DS_TYPES PT ON " +
            " PT.PRIMARY_DS_TYPE_ID=P.PRIMARY_DS_TYPE_ID WHERE ";
	if ((cond.getPrimaryDSName()).indexOf('_') != -1 || (cond.getPrimaryDSName()).indexOf('%') != -1)
	    sql += " P.PRIMARY_DS_NAME like ?";
	else  sql += " P.PRIMARY_DS_NAME = ?";
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, cond.getPrimaryDSName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("PRIMARY_DS_NAME");
                int pID = rs.getInt("PRIMARY_DS_ID");
                int typeID = rs.getInt("PRIMARY_DS_TYPE_ID");
                String typeName = rs.getString("PRIMARY_DS_TYPE");
                int dt = rs.getInt("CREATION_DATE");
                String by = rs.getString("CREATE_BY");
                this.result.put(new PrimaryDataset(pID, name, new PrimaryDSType(typeID, typeName), dt,by));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }


    //list entire primary_dataset table
    public JSONArray listAllPrimaryDatasets(Connection conn) throws Exception{
        String sql = "SELECT P.PRIMARY_DS_ID, P.PRIMARY_DS_NAME, P.PRIMARY_DS_TYPE_ID, PT.PRIMARY_DS_TYPE, " +
            "P.CREATION_DATE, P.CREATE_BY FROM " +
             schemaOwner + "PRIMARY_DATASETS P " +
            "JOIN " + schemaOwner + "PRIMARY_DS_TYPES PT ON " +
            " PT.PRIMARY_DS_TYPE_ID=P.PRIMARY_DS_TYPE_ID";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
	    //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("PRIMARY_DS_NAME");
                int pID = rs.getInt("PRIMARY_DS_ID");
                int typeID = rs.getInt("PRIMARY_DS_TYPE_ID");
                String typeName = rs.getString("PRIMARY_DS_TYPE");
                int dt = rs.getInt("CREATION_DATE");
                String by = rs.getString("CREATE_BY");
                this.result=new JSONArray();
		this.result.put(new PrimaryDataset(pID, name, new PrimaryDSType(typeID, typeName), dt, by));
            }
	}finally {
	       if (rs != null) rs.close();
               if (ps != null) ps.close();
             }
            return this.result;
    }
    //list only the primary dataset that satisfied the condition.
    public JSONArray listPrimaryDatasets(Connection conn, JSONArray cond) throws Exception{
	this.result = new JSONArray();
	int condSize = 0;
        condSize = cond.length();
	PreparedStatement ps = null; 
        ResultSet rs = null;
	String sql = "SELECT P.PRIMARY_DS_ID, P.PRIMARY_DS_NAME, P.PRIMARY_DS_TYPE_ID, PT.PRIMARY_DS_TYPE, " +
	    "P.CREATION_DATE, P.CREATE_BY FROM " +
	    schemaOwner + "PRIMARY_DATASETS P " +
	    "JOIN " + schemaOwner + "PRIMARY_DS_TYPES PT ON " +
	    " PT.PRIMARY_DS_TYPE_ID=P.PRIMARY_DS_TYPE_ID WHERE ";
	for (int i=0; i<condSize; i++){
	    PrimaryDataset c = (PrimaryDataset)cond.getJSONObject(i);
	    if (i==0){
	        if ((c.getPrimaryDSName()).indexOf('_') != -1 || (c.getPrimaryDSName()).indexOf('%') != -1)
		sql += " P.PRIMARY_DS_NAME like ?";
		else  sql += " P.PRIMARY_DS_NAME = ?";
	    }
	    else{
		if ((c.getPrimaryDSName()).indexOf('_') != -1 || (c.getPrimaryDSName()).indexOf('%') != -1)
		sql +=  " or P.PRIMARY_DS_NAME like ?";
		else sql +=  " or P.PRIMARY_DS_NAME = ?";
	    }
	}
        ps = null;
	rs = null;
	try{
	    ps = DBManagement.getStatement(conn, sql);
	    //prepare statement index starting with 1, but JSONArray index starting with 0.
	    for (int i=1; i<=condSize; i++){
		PrimaryDataset c = (PrimaryDataset)cond.getJSONObject(i-1);
		ps.setString(i, c.getPrimaryDSName());
	    }
	    //System.out.println(ps.toString());
	    rs =  ps.executeQuery();
	    while(rs.next()){
		String name = rs.getString("PRIMARY_DS_NAME");
		int pID = rs.getInt("PRIMARY_DS_ID");
		int typeID = rs.getInt("PRIMARY_DS_TYPE_ID");
		String typeName = rs.getString("PRIMARY_DS_TYPE");
		int dt = rs.getInt("CREATION_DATE");
		String by = rs.getString("CREATE_BY");
		this.result.put(new PrimaryDataset(pID, name, new PrimaryDSType(typeID, typeName), dt,by));
	    } 
	}finally {
		if (rs != null) rs.close();
		if (ps != null) ps.close();
	    }
	    return this.result;
    }
    PrimaryDSType listPrimaryDSType(Connection conn, String typeName) throws Exception{
	String sql = "SELECT PT.PRIMARY_DS_TYPE_ID FROM " + schemaOwner + "PRIMARY_DS_TYPES PT "
		    + " WHERE PT.PRIMARY_DS_TYPE ";
	if (typeName.indexOf('_') != -1 || typeName.indexOf('%') != -1)
	    sql += " = ?";
	else sql += " like ?";
	PreparedStatement ps = null;
        ResultSet rs = null;
	int PTId = 0;
	try{
            ps = DBManagement.getStatement(conn, sql);
	    ps.setString(1, typeName);
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            int i =0;
            while(rs.next()){
		PTId = rs.getInt("PRIMARY_DS_TYPE_ID");
		i++;
		if( i > 1 ) {
		    throw new DBSException("Input Data Error", "More than one primary dataset type found when only one is expected");
		}	
	    }
	}
	finally{
	    if (rs != null) rs.close();
	    if (ps != null) ps.close();
	}
	if (PTId == 0){ 
	    throw new DBSException("Data not Found in DB", "Primary dataset type: " + typeName + " not found in DB ");
	}else{
	    return new PrimaryDSType(PTId, typeName);
	}
    }//end listPrimaryDSType
}
