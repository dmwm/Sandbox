/***
 * $Id: FileTypeQO.java,v 1.1 2009/10/19 15:05:16 yuyi Exp $
 *
 * This is the class for file type query Object.
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
import cms.dbs.dataobjs.FileType;

public class FileTypeQO extends  DBSSimpleQueryObject{

    public FileTypeQO() throws Exception{
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
        //list only the file type that satisfied the condition.
    public JSONArray listFileTypes(Connection conn, FileType cond) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT F.FILE_TYPE_ID, F.FILE_TYPE FROM " +
            schemaOwner + " FILE_TYPES F  WHERE" ;
	if ((cond.getFileType()).indexOf('_') != -1 || (cond.getFileType()).indexOf('%') != -1)
	    sql += " F.FILE_TYPE like ?";
	else  sql += " F.FILE_TYPE = ?";
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, cond.getFileType());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("FILE_TYPE");
                int pID = rs.getInt("FILE_TYPE_ID");
                this.result.put(new FileType(pID, name));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
}
