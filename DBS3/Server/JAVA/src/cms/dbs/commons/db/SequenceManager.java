/**
 * $Id: SequenceManager.java,v 1.1 2009/09/23 19:23:17 yuyi Exp $
 * This is a class handling database sequence.
 * Author Y. Guo
 *
 **/

package cms.dbs.commons.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import cms.dbs.commons.db.DBManagement;
import cms.dbs.commons.utils.DBSSrvcConfig;

public class SequenceManager{

    public SequenceManager(){}

    public static int getSequence(Connection conn, String seqName) throws Exception{
	int seq = 0;
	Statement stmt = null;
	try {
	    DBSSrvcConfig config = DBSSrvcConfig.getInstance();
            String schemaOwner = config.getSchemaOwner();
	    String sql = "select " + schemaOwner + seqName + ".nextval as val "+ " from dual";
	    stmt = conn.createStatement();
	    ResultSet resultSet = stmt.executeQuery(sql);
	    while(resultSet.next()){
		seq = resultSet.getInt("val");
	    }
	}
	finally{
	    if(stmt != null)stmt.close();
	}
	return seq;
    }





}
