/**
 * 
 $Revision: 1.7 $"
 $Id: FileLumi.java,v 1.7 2009/10/20 16:30:29 yuyi Exp $"
 *
 * Data Object from table : FILE_LUMIS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;
import cms.dbs.dataobjs.File;

public class FileLumi extends JSONObject  {

	public FileLumi ( ) {

	}
		
        public FileLumi ( int fileLumiID, int runNum, int lumiSectionNum, File fileDO ) throws Exception  {
		
                this.putOnce("FILE_LUMI_ID", fileLumiID );
                this.putOnce("RUN_NUM", runNum );
                this.putOnce("LUMI_SECTION_NUM", lumiSectionNum );
                this.putOnce("FILE_DO", fileDO );
        }

	public void setFileLumiID (int fileLumiID) throws Exception {
 		this.put( "FILE_LUMI_ID", fileLumiID );
	}
	
	public void setRunNum (int runNum) throws Exception {
 		this.put( "RUN_NUM", runNum );
	}
	
	public void setLumiSectionNum (int lumiSectionNum) throws Exception {
 		this.put( "LUMI_SECTION_NUM", lumiSectionNum );
	}
	
	public void setFileDO (File fileDO) throws Exception {
 		this.put( "FILE_DO", fileDO );
	}
	
	public int getFileLumiID ( )  throws Exception {
		int fileLumiID = 0;
               	if (!JSONObject.NULL.equals(this.get("FILE_LUMI_ID"))) {
                       	fileLumiID = this.getInt("FILE_LUMI_ID");
               	}
                return fileLumiID;
        }
	
	public int getRunNum ( )  throws Exception {
		int runNum = 0;
               	if (!JSONObject.NULL.equals(this.get("RUN_NUM"))) {
                       	runNum = this.getInt("RUN_NUM");
               	}
                return runNum;
        }
	
	public int getLumiSectionNum ( )  throws Exception {
		int lumiSectionNum = 0;
               	if (!JSONObject.NULL.equals(this.get("LUMI_SECTION_NUM"))) {
                       	lumiSectionNum = this.getInt("LUMI_SECTION_NUM");
               	}
                return lumiSectionNum;
        }
	
	File getFileDO ( )  throws Exception {
		File fileDO = null;
               	if (!JSONObject.NULL.equals(this.get("FILE_DO"))) {
                       	fileDO = (File)this.getJSONObject("FILE_DO");
               	}
                return fileDO;
        }
	
}
