/***
 * $Id: FileLumiQO.java,v 1.3 2009/10/22 15:30:42 yuyi Exp $
 *
 * This is the class for file Lumi query Object.
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
import cms.dbs.dataobjs.File;
import cms.dbs.dataobjs.FileLumi;

public class FileLumiQO extends  DBSSimpleQueryObject{

    public FileLumiQO() throws Exception{
            super();
    }
    //Bulk insert into FILE_LUMIS
    public void putFileLumiBatch(Connection conn, JSONArray cond) throws Exception{
       int seqSize = 1000; //this decided by the schema
       String seqName = "SEQ_FLM";
        String tableName = "FILE_LUMIS";
       this.insertTableBatch(conn, cond, tableName, seqName, seqSize);						    
   }

        //list run # and lumi sections # when knowing the file
    public JSONArray listFileLumis(Connection conn, File file) throws Exception{
        JSONArray myResult = new JSONArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT FL.FILE_LUMI_ID, FL.RUN_NUM, FL.LUMI_SECTION_NUM, FL.FILE_ID, "
	    +" F.LOGICAL_FILE_NAME LFN, F.IS_FILE_VALID, F.DATASET_ID, F.BLOCK_ID, F.FILE_TYPE_ID, "
	    + " F.CHECK_SUM, F.EVENT_COUNT, F.FILE_SIZE,  F.BRANCH_HASH_ID, F.ADLER32, F.MD5, F.AUTO_CROSS_SECTION,"
	    + " F.CREATION_DATE, F.CREATE_BY, F.LAST_MODIFICATION_DATE, F.LAST_MODIFIED_BY "
	    + "FROM " +
            schemaOwner + "FILE_LUMIS FL"
	    +" JOIN " +  schemaOwner +"FILES F on F.FILE_ID=FL.FILE_ID " 
	    +" WHERE F.LOGICAL_FILE_NAME " ;
	if ((file.getLogicalFileName()).indexOf('%') != -1)
	    sql += " like ?";
	else  sql += "  = ?";
        ps = null;
        rs = null;
        try{
            ps = DBManagement.getStatement(conn, sql);
            //prepare statement index starting with 1, but JSONArray index starting with 0.
	    ps.setString(1, file.getLogicalFileName());
            //System.out.println(ps.toString());
            rs =  ps.executeQuery();
            while(rs.next()){
		int flID = rs.getInt("FILE_LUMI_ID");
		int fID = rs.getInt("FILE_ID");
                String fLFN = rs.getString("LFN");
		int run = rs.getInt("RUN_NUM");
		int lSec = rs.getInt("LUMI_SECTION_NUM");
		myResult.put(new FileLumi(flID, run, lSec, new File(fID, fLFN)));
            }
        }finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            return myResult;
    }
}
