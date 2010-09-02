/***
 * $Id: FileBO.java,v 1.6 2009/11/30 22:08:59 afaq Exp $
 *
 * This is the class for File business objects.
 * @author Y. Guo  Oct-20-09
 ***/
package cms.dbs.bizobjs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONArray;
import cms.dbs.commons.exceptions.DBSException;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.queryobjs.DatasetQO;
import cms.dbs.dataobjs.File;
import cms.dbs.dataobjs.Block;
import cms.dbs.dataobjs.FileParent;
import cms.dbs.dataobjs.FileLumi;
import cms.dbs.queryobjs.FileLumiQO;
import cms.dbs.queryobjs.FileQO;
import cms.dbs.queryobjs.BlockQO;
import cms.dbs.queryobjs.FileParentQO;
import cms.dbs.commons.utils.DBSSrvcUtil;

public class FileBO extends DBSBusinessObject{
    
    public FileBO() throws Exception {
	super();
    }

    public JSONArray getFiles(Connection conn, File cond) throws Exception{
	FileQO file = new  FileQO();
	return file.listFiles(conn, cond);
	
    }

    public JSONArray getFiles(Connection conn, JSONArray cond) throws Exception{
        FileQO file = new  FileQO();
        return file.listFiles(conn, cond);
        
    }

    public void insertFile(Connection conn, File cond, JSONArray fps, JSONArray fls) throws Exception{
	FileQO fileQO = new  FileQO();
	try {
		fileQO.putFile(conn, cond);
        
	}catch (SQLException ex) {
        	String exmsg = ex.getMessage();
            	if(!exmsg.startsWith("Duplicate entry") && !exmsg.startsWith("ORA-00001: unique constraint") ) throw ex;
            	else {
			//System.out.println("Reset file ID and re-fetch File from database");
			//Reset file ID and re-fetch File from database
			cond.setFileID(0);
			JSONArray allfiles = fileQO.listFiles(conn, cond);
			if(allfiles.length() != 1 ) throw new DBSException("Input Data Error", "One file should have been found in database:"+cond);
			cond.setFileID(((File)allfiles.getJSONObject(0)).getFileID());
		}
        }

	FileParentQO fileParentQO = new FileParentQO(); 
	//

	//System.out.println("Here we go....");	
	//Only if there are parents of this file 
	if (fps.length() > 0 ) {
		JSONArray p  = new JSONArray();
		for (int i =0; i<fps.length(); i++){
			//System.out.println("I am NOT here.................");
	    		p.put(((FileParent)(fps.getJSONObject(i))).getParentFileDO());
		}

		//System.out.println("\n **** list bared file ****");
		JSONArray ps = fileQO.listBaredFiles(conn, p);
		//System.out.println("\n **** list bared file again !****");
		if((ps.length() == 0) || (ps.length() != fps.length()))
			throw new DBSException("Input data error", "No parent file found as  "+ fps);
		//System.out.println("\nInsert Parantages");
		for (int i =0; i<fps.length(); i++){
	    		fps.put(i, new FileParent(0, cond, (File)ps.getJSONObject(i)));
		}
		fileParentQO.putFileParentBatch(conn, fps);
	}

	 //Only if there are lumi sections for this file
	if (fls.length() > 0 ) {
		//System.out.println("\nInsert Lumi");
		FileLumiQO flQO = new FileLumiQO();
		for(int i=0; i<fls.length();i++){
	    		((FileLumi)fls.getJSONObject(i)).setFileDO(cond);
		}
		flQO.putFileLumiBatch(conn, fls);
	}
	//
	conn.commit(); 
   }

    public JSONArray getFileLumis(Connection conn, File file) throws Exception{
	FileLumiQO flQO = new FileLumiQO();	
	return flQO.listFileLumis(conn, file);
   }

    public JSONArray getFileParents(Connection conn, File file) throws Exception{
	FileParentQO fpQO = new FileParentQO();
	    return fpQO.listParentFiles(conn, file);
	}
   
}
