/***
 * $Id: PrimaryDatasetBO.java,v 1.5 2009/10/16 18:35:33 yuyi Exp $
 *
 * This is the class for primary dataset business objects.
 * @author Y. Guo
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

public class PrimaryDatasetBO extends DBSBusinessObject{
    
    public PrimaryDatasetBO() throws Exception {
	super();
    }

    public JSONArray getPrimaryDatasets(Connection conn, PrimaryDataset cond) throws Exception{
	PrimaryDatasetQO primaryDS = new  PrimaryDatasetQO();
	return primaryDS.listPrimaryDatasets(conn, cond);
	
    }

   public PrimaryDataset insertPrimaryDataset(Connection conn, PrimaryDataset cond) throws Exception{
	PrimaryDatasetQO primaryDS = new  PrimaryDatasetQO();
	PrimaryDataset result = primaryDS.putPrimaryDataset(conn, cond);
	conn.commit(); 
	return result;
   }

}
