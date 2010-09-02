/***
 * $Id: DBSApis.java,v 1.13 2009/11/11 22:52:37 afaq Exp $
 * DBS Server side APIs .
 * @author Y. Guo
 ***/
package cms.dbs.apis;

import java.sql.*;
import org.json.JSONObject;
import org.json.JSONArray;
import cms.dbs.dataobjs.PrimaryDataset;
import cms.dbs.dataobjs.PrimaryDSType;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.dataobjs.ProcessedDataset;
import cms.dbs.dataobjs.DataTier;
import cms.dbs.dataobjs.DatasetType;
import cms.dbs.dataobjs.File;
import cms.dbs.dataobjs.FileLumi;
import cms.dbs.dataobjs.FileParent;
import cms.dbs.dataobjs.PhysicsGroup;
import cms.dbs.dataobjs.Block;
import cms.dbs.dataobjs.FileType;
import cms.dbs.bizobjs.PrimaryDatasetBO;
import cms.dbs.bizobjs.DatasetBO;
import cms.dbs.bizobjs.FileBO;
import cms.dbs.bizobjs.BlockBO;
import cms.dbs.commons.exceptions.DBSException;
import cms.dbs.commons.utils.DBSSrvcConfig;
import cms.dbs.commons.db.DBManagement;




public class DBSApis {
    private Connection conn = null;
    
    public DBSApis() throws Exception{
	conn = getConnection();
	conn.setAutoCommit(false);  
    }

    private Connection getConnection() throws Exception{
        DBSSrvcConfig config = DBSSrvcConfig.getInstance();
        return DBManagement.getConnection(config.getDbDriver(),
            config.getDbURL(), config.getDbUserName(), config.getDbUserPasswd());
    }

    public void finalize() throws Exception {
        if (conn != null) conn.close();        // close open connection
    }

    private void closeConnection() throws Exception{
        if(conn != null) conn.close();
    }

    public JSONObject DBSApiFindBlocks(Block bk) throws Exception{
        JSONArray result = new JSONArray();
        BlockBO bkBO = new BlockBO();
        result = bkBO.getBlocks(conn, bk);
        JSONObject retn = new JSONObject();
        retn.putOnce("input", bk);
        retn.putOnce("result", result);
        return retn;
    }
  
    public void DBSApiInsertBlock(Block cd) throws Exception{
        BlockBO bkBO = new BlockBO();
        bkBO.insertBlock(conn, cd);
    }

    public JSONObject DBSApiFindFiles(File file) throws Exception{
        JSONArray result = new JSONArray();
        FileBO fBO = new FileBO();
        result = fBO.getFiles(conn, file);
        JSONObject retn = new JSONObject();
        retn.putOnce("input", file);
        retn.putOnce("result", result);
        return retn;
    }

    public JSONObject DBSApiFindFiles(JSONArray files) throws Exception{
        JSONArray result = new JSONArray();
        FileBO fBO = new FileBO();
        result = fBO.getFiles(conn, files);
        JSONObject retn = new JSONObject();
        retn.putOnce("input", files);
        retn.putOnce("result", result);
        return retn;
    }

    public void DBSApiInsertFile(File f, JSONArray fps, JSONArray fls) throws Exception{
	FileBO fBO = new FileBO();	
	fBO.insertFile(conn, f, fps,fls);
    }

    public JSONObject DBSApiFindLumi4File(File file) throws Exception{
        JSONArray result = new JSONArray();
        FileBO fBO = new FileBO();
        result = fBO.getFileLumis(conn, file);
        JSONObject retn = new JSONObject();
        retn.putOnce("input", file);
        retn.putOnce("result", result);
        return retn;
    }	

    public JSONObject DBSApiFindParents4File(File file) throws Exception{
        JSONArray result = new JSONArray();
        FileBO fBO = new FileBO();
        result = fBO.getFileParents(conn, file);
        JSONObject retn = new JSONObject();
        retn.putOnce("input", file);
        retn.putOnce("result", result);
        return retn;
    }

    public JSONObject DBSApiFindDatasets(Dataset cd) throws Exception{
	JSONArray result = new JSONArray();
	DatasetBO dBO = new DatasetBO();
	result = dBO.getDatasets(conn, cd);
	JSONObject retn = new JSONObject();
        retn.putOnce("input", cd);
        retn.putOnce("result", result);
        return retn;
    }

    public int DBSApiInsertDataset(Dataset cd) throws Exception{
        DatasetBO dBO = new DatasetBO();
        dBO.insertDataset(conn, cd);
	return 1;
    }

    public JSONObject DBSApiFindPrimaryDatasets( PrimaryDataset cd) throws Exception{
	JSONArray result = new JSONArray();
	PrimaryDatasetBO pBO = new PrimaryDatasetBO();
	result = pBO.getPrimaryDatasets(conn, cd);
	JSONObject retn = new JSONObject();
	retn.putOnce("input", cd);
	retn.putOnce("result", result);
	return retn;
    }

    public PrimaryDataset DBSApiInsertPrimaryDataset(PrimaryDataset cd) throws Exception{
	PrimaryDatasetBO pBO = new PrimaryDatasetBO();
	return pBO.insertPrimaryDataset(conn, cd);
    }//end DBSApiInsertPrimaryDataset
    
    public static void main (String args[]){
	try{
            DBSApis api = new DBSApis();
	    //
	    System.out.println("\n ***Test DBSApiFindPrimaryDatasets API ***");
            PrimaryDataset cd2 = new PrimaryDataset(0, "TES%", null, 0, "");
            JSONArray result2 = (api.DBSApiFindPrimaryDatasets(cd2)).getJSONArray("result");
            for(int i=0; i<result2.length();i++){
                 System.out.println(result2.optJSONObject(i));
            }
	    //
	    System.out.println("\n ***Test DBSApiFindDatasets API ***");
	    Dataset dataset = new Dataset(0, "/TTbar/Summer09-MC_31X_V3-v1/GEN-SIM-RAW");
	    JSONArray datasets = (api.DBSApiFindDatasets(dataset)).getJSONArray("result");
	    for(int i=0; i<datasets.length();i++){
		System.out.println(datasets.optJSONObject(i));
	    }
	    // 
	    System.out.println("\n ***Test Insert primary dataset API ***");
	    PrimaryDSType PT = new PrimaryDSType(0, "test");
	    PrimaryDataset PD = new PrimaryDataset(0, "Primary-Yuyi4", PT, 0, "");
	    System.out.println((api.DBSApiInsertPrimaryDataset(PD)).toString());
	    //
	    System.out.println("\n ***Test Insert dataset API ***");
	    ProcessedDataset processedDS = new  ProcessedDataset(0, "ProcessedDS-Yuyi4");
	    Dataset ds = new Dataset(0, "/TEST10-Primary/ProcessedDS-Yuyi4/GEN-SIM-RAW", 1, PD, processedDS, new DataTier(0, "GEN-SIM-RAW"), 
				new DatasetType(0, "PRODUCTION"), null,
				null, new PhysicsGroup(6, "QCD"), 0.01, "Yuyi's Tag", 0, "");
	    System.out.println(api.DBSApiInsertDataset(ds));
	    //
	    System.out.println("\n *** Test Insert Block API ***");
	    Block bki = new Block(0, "/TTbar/Summer09-MC_31X_V3-v1/GEN-SIM-RAW#f99b4c09-a68f-4e73-8f4c-560c1fa922fc");
	    api.DBSApiInsertBlock(bki);
	    
	    //
	    System.out.println("\n *** Test list Block API ***");
	    Block bk = new Block(0, "/TTbar/Summer09-MC_31X_V3-v1/GEN-SIM-RAW#d%");
	    System.out.println(api.DBSApiFindBlocks(bk));
	    //
	    System.out.println("\n *** Test list Files API ***");
            String LFN = "/store/mc/Summer09/TTbar/GEN-SIM-RAW/MC_31X_V3-v1/0025/60%.root";
	    File file = new File(0, LFN);
            System.out.println(api.DBSApiFindFiles(file));
	    //
	    System.out.println("\n *** Test List Array of Files API ***");
	    JSONArray files = new JSONArray();
	    files.put(new File(0,"/store/mc/Summer09/TTbar/GEN-SIM-RAW/MC_31X_V3-v1/0025/6C405563-6988-DE11-A300-003048C56E54.root"));
	    files.put(new File(0,"/store/mc/Summer09/TTbar/GEN-SIM-RAW/MC_31X_V3-v1/0025/DC63A151-AA88-DE11-B146-00163691DD05.root"));
	    System.out.println(api.DBSApiFindFiles(files));
	    	    //
	    System.out.println("\n *** Test Insert File API ***");
	    File f1 = new File(0, "Yuyi-Test-17.root", 1, new Dataset(0, "/TTbar/YUYI-TEST6/GEN-SIM-RAW"), 
			    new Block(0, "/TTbar/Summer09-MC_31X_V3-v1/GEN-SIM-RAW#f99b4c09-a68f-4e73-8f4c-560c1fa922fc"),
			    new FileType(1, "EDM"), "THis is a Check sum", 100, 134, null, "this is asler32", "this is MD5",
			    0.23 ,0, "", 0, "");
	    //System.out.println("*** File to be inserted *******\n");
	    //System.out.println(f1);
	    JSONArray fls = new JSONArray();
	    for (int i=1; i<101; i++){
		//fls.put(new FileLumi(0, 111, i, new File(0, "Yuyi-Test-11.root")));
		fls.put(new FileLumi(0, 171, i, f1));
	    }
	    JSONArray fps = new JSONArray();
	    fps.put(new FileParent(0, f1, 
	    new File(0, "/store/mc/Summer09/TTbar/GEN-SIM-RAW/MC_31X_V3-v1/0025/6C405563-6988-DE11-A300-003048C56E54.root"))); 
	    api.DBSApiInsertFile(f1, fps, fls);
	    System.out.println("\n *** Done test Insert File API ***");
	    //
	    System.out.println("\n *** Test findLumi4File API ***");
	    System.out.println(api.DBSApiFindLumi4File(f1));
	    //
	    System.out.println("\n *** Test findParents4File API ***");
	    System.out.println(api.DBSApiFindParents4File(f1));
	    //close the connection before you leave.
	    api.closeConnection();

	}
	catch (DBSException ex){
	    System.out.println("DBSException raised :" + ex.getMessage() + ". " + ex.getDetail());
	}
	catch(Exception ex){
	    System.out.println("Exception raised :" + ex.getMessage() + ". " );
	}
    }
}
