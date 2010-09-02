/***
 * $Id: BlockBO.java,v 1.3 2009/11/11 22:52:37 afaq Exp $
 *
 * This is the class for Block business objects.
 * @author Y. Guo  Oct-20-09
 ***/
package cms.dbs.bizobjs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;
import cms.dbs.dataobjs.Block;
import cms.dbs.queryobjs.BlockQO;
import cms.dbs.commons.utils.DBSSrvcUtil;

public class BlockBO extends DBSBusinessObject{
    
    public BlockBO() throws Exception {
	super();
    }

    public JSONArray getBlocks(Connection conn, Block cond) throws Exception{
	BlockQO bk = new  BlockQO();
	return bk.listBlocks(conn, cond);
	
    }

   public void insertBlock(Connection conn, Block cond) throws Exception{
	BlockQO bk = new BlockQO();
	try {
		bk.putBlock(conn, cond);
	}catch (SQLException ex) { 
            String exmsg = ex.getMessage();
            if(!exmsg.startsWith("Duplicate entry") && !exmsg.startsWith("ORA-00001: unique constraint") ) throw ex;
            else DBSSrvcUtil.writeLog( cond + " Already Exists");
        }	 
	conn.commit();
   }

}
