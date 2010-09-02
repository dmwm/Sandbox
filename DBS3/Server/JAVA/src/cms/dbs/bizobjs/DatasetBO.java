/***
 * $Id: DatasetBO.java,v 1.2 2009/11/11 22:52:37 afaq Exp $
 *
 * This is the class for dataset business objects.
 * @author Y. Guo  Oct-13-09
 ***/
package cms.dbs.bizobjs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;
import cms.dbs.dataobjs.PrimaryDataset;
import cms.dbs.dataobjs.PrimaryDSType;
import cms.dbs.queryobjs.PrimaryDatasetQO;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.queryobjs.DatasetQO;
import cms.dbs.commons.utils.DBSSrvcUtil;

public class DatasetBO extends DBSBusinessObject{
    
    public DatasetBO() throws Exception {
	super();
    }

    public JSONArray getDatasets(Connection conn, Dataset cond) throws Exception{
	DatasetQO dataset = new  DatasetQO();
	return dataset.listDatasets(conn, cond);
	
    }

   public void insertDataset(Connection conn, Dataset cond) throws Exception{
	DatasetQO dataset = new  DatasetQO();
	try {
		dataset.putDataset(conn, cond);
	  }catch (SQLException ex) {
            String exmsg = ex.getMessage();
            if(!exmsg.startsWith("Duplicate entry") && !exmsg.startsWith("ORA-00001: unique constraint") ) throw ex;
            else DBSSrvcUtil.writeLog( cond + " Already Exists");
        }
	conn.commit(); 
   }

}
