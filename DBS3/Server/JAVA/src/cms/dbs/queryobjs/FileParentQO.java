/***
 * $Id: FileParentQO.java,v 1.2 2009/10/22 15:30:42 yuyi Exp $
 *
 * This is the class for file parent query Object.
 * @author Y. Guo
 ***/
package cms.dbs.queryobjs;

import java.util.Iterator;
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
import cms.dbs.dataobjs.FileParent;
import cms.dbs.dataobjs.File;

public class FileParentQO extends  DBSSimpleQueryObject{

    public FileParentQO() throws Exception{
            super();
    }
    //Bulk insert into FILE_PARENTS
    public void putFileParentBatch(Connection conn, JSONArray cond) throws Exception{
       int seqSize = 120; //this decided by the schema
       String seqName = "SEQ_FP";
       String tableName = "FILE_PARENTS";
       int alen = cond.length();
        int acc = 0;
        for(int i =0; i<alen; i++){
            int id = 0;
            if(acc==0 || acc == seqSize){
                id = SequenceManager.getSequence(conn, seqName);
                acc=0;
            }
            JSONObject ob = cond.getJSONObject(i);
            Iterator it = ob.keys();
            while (it.hasNext()){
                String key = (String)it.next();
                if(key.endsWith("_ID"))ob.put(key, id+acc);
            }
            acc++;
        }
       String sql = "insert into "+ schemaOwner + tableName +"(THIS_FILE_ID, PARENT_FILE_ID,FILE_PARENT_ID) values(?,?,?)";
       PreparedStatement ps = null;
       try{
            ps = DBManagement.getStatement(conn, sql);
	    for(int j=0; j<cond.length();j++){
                FileParent fp = (FileParent)cond.getJSONObject(j);
		ps.setLong(1, fp.getThisFileDO().getFileID());
		ps.setLong(2, fp.getParentFileDO().getFileID());
		ps.setLong(3, fp.getFileParentID());
		ps.addBatch();
	    }
	    //System.out.println(ps.toString());
            ps.executeBatch();
	}catch (SQLException ex) {
            String exmsg = ex.getMessage();
            if(!exmsg.startsWith("Duplicate entry") && !exmsg.startsWith("ORA-00001: unique constraint") ) throw ex;
            else DBSSrvcUtil.writeLog( cond + " Already Exists");
        } finally {
            if (ps != null) ps.close();
        }		
   }

        //list parent files when knowing the child
    public JSONArray listParentFiles(Connection conn, File child) throws Exception{
        this.result = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT FP.FILE_PARENT_ID FP_ID, FP.THIS_FILE_ID CHILD_ID, FP.PARENT_FILE_ID PARENT_ID, "
	    + "F1.LOGICAL_FILE_NAME CHILD_LFN, F2.LOGICAL_FILE_NAME PARENT_LFN FROM " +
            schemaOwner + "FILE_PARENTS FP"
	    +" JOIN " +  schemaOwner +"FILES F1 on F1.FILE_ID=FP.THIS_FILE_ID " 
	    +" JOIN " +  schemaOwner +"FILES F2 on F2.FILE_ID=FP.PARENT_FILE_ID "
	    +" WHERE F1.LOGICAL_FILE_NAME " ;
	if ((child.getLogicalFileName()).indexOf('_') != -1 || (child.getLogicalFileName()).indexOf('%') != -1)
	    sql += " like ?";
	else  sql += "  = ?";
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, child.getLogicalFileName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
		int fpID = rs.getInt("FP_ID");
		int childID = rs.getInt("CHILD_ID");
		int parentID = rs.getInt("PARENT_ID");
                String childLFN = rs.getString("CHILD_LFN");
		String parentLFN = rs.getString("PARENT_LFN");
                this.result.put(new FileParent(fpID, new File(childID, childLFN), new File(parentID, parentLFN)));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return this.result;
    }
}
