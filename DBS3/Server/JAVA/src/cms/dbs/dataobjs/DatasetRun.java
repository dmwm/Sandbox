/**
 * 
 $Revision: 1.3 $"
 $Id: DatasetRun.java,v 1.3 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : DATASET_RUNS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class DatasetRun extends JSONObject  {

	public DatasetRun ( ) {

	}
		
        public DatasetRun ( int pathRunID, int datasetID, int runNumber, int complete, int lumiSectionCount, int creationDate, String createBy ) throws Exception  {
		
                this.putOnce("PATH_RUN_ID", pathRunID );
                this.putOnce("DATASET_ID", datasetID );
                this.putOnce("RUN_NUMBER", runNumber );
                this.putOnce("COMPLETE", complete );
                this.putOnce("LUMI_SECTION_COUNT", lumiSectionCount );
                this.putOnce("CREATION_DATE", creationDate );
                this.putOnce("CREATE_BY", createBy );
        }

	public void setPathRunID (int pathRunID) throws Exception {
 		this.put( "PATH_RUN_ID", pathRunID );
	}
	
	public void setDatasetID (int datasetID) throws Exception {
 		this.put( "DATASET_ID", datasetID );
	}
	
	public void setRunNumber (int runNumber) throws Exception {
 		this.put( "RUN_NUMBER", runNumber );
	}
	
	public void setComplete (int complete) throws Exception {
 		this.put( "COMPLETE", complete );
	}
	
	public void setLumiSectionCount (int lumiSectionCount) throws Exception {
 		this.put( "LUMI_SECTION_COUNT", lumiSectionCount );
	}
	
	public void setCreationDate (int creationDate) throws Exception {
 		this.put( "CREATION_DATE", creationDate );
	}
	
	public void setCreateBy (String createBy) throws Exception {
 		this.put( "CREATE_BY", createBy );
	}
	
	int getPathRunID ( )  throws Exception {
		int pathRunID = 0;
               	if (!JSONObject.NULL.equals(this.get("PATH_RUN_ID"))) {
                       	pathRunID = this.getInt("PATH_RUN_ID");
               	}
                return pathRunID;
        }
	
	int getDatasetID ( )  throws Exception {
		int datasetID = 0;
               	if (!JSONObject.NULL.equals(this.get("DATASET_ID"))) {
                       	datasetID = this.getInt("DATASET_ID");
               	}
                return datasetID;
        }
	
	int getRunNumber ( )  throws Exception {
		int runNumber = 0;
               	if (!JSONObject.NULL.equals(this.get("RUN_NUMBER"))) {
                       	runNumber = this.getInt("RUN_NUMBER");
               	}
                return runNumber;
        }
	
	int getComplete ( )  throws Exception {
		int complete = 0;
               	if (!JSONObject.NULL.equals(this.get("COMPLETE"))) {
                       	complete = this.getInt("COMPLETE");
               	}
                return complete;
        }
	
	int getLumiSectionCount ( )  throws Exception {
		int lumiSectionCount = 0;
               	if (!JSONObject.NULL.equals(this.get("LUMI_SECTION_COUNT"))) {
                       	lumiSectionCount = this.getInt("LUMI_SECTION_COUNT");
               	}
                return lumiSectionCount;
        }
	
	int getCreationDate ( )  throws Exception {
		int creationDate = 0;
               	if (!JSONObject.NULL.equals(this.get("CREATION_DATE"))) {
                       	creationDate = this.getInt("CREATION_DATE");
               	}
                return creationDate;
        }
	
	String getCreateBy ( )  throws Exception {
		String createBy = null;
               	if (!JSONObject.NULL.equals(this.get("CREATE_BY"))) {
                       	createBy = this.getString("CREATE_BY");
               	}
                return createBy;
        }
	
}