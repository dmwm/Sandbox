/***
 * $Id: Files.java,v 1.5 2009/11/30 22:08:57 afaq Exp $
 * DBS Server side APIs .
 ***/

package cms.dbs.apis;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

import org.json.JSONObject;
import org.json.JSONArray;

import cms.dbs.dataobjs.File;
import cms.dbs.dataobjs.Dataset;
import cms.dbs.dataobjs.Block;
import cms.dbs.dataobjs.FileLumi;
import cms.dbs.dataobjs.FileParent;
import cms.dbs.dataobjs.FileType;

import cms.dbs.apis.DBSApis;

import cms.dbs.commons.exceptions.DBSException;
import org.restlet.data.Status;

import cms.dbs.commons.utils.DBSSrvcUtil;

public class Files extends Resource {

    String dataset;
    String lfn;
    String block;

    public Files(Context context, Request request, Response response) {
	
        super(context, request, response);

        String req=(String) request.getResourceRef().toString();
        java.util.HashMap kvalues = DBSSrvcUtil.getUrlParams(req);
	//A file search can be based on any of the following
        this.dataset = (String)kvalues.get("dataset");
        this.lfn = (String)kvalues.get("lfn");
        this.block = (String)kvalues.get("block");

	//FIXME: We should check if comple path is provided here or NOT

         // Allow modifications of this resource via POST/PUT/DELETE requests.  
         setModifiable(true);

        // This resource has only one type of representation.  
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));

//response.setEntity("TEST", MediaType.TEXT_PLAIN);

    }

    //GET  http://.../files
    //GET  http://.../files?dataset=/{PRIMARY_DATASET_NAME}/{PROCESSED_DATASET_NAME}/{DATA_TIER}
    //GET  http://.../files?lfn=/store/...
    //GET  http://.../files?block=/{PRIMARY_DATASET_NAME}/{PROCESSED_DATASET_NAME}/{DATA_TIER}#GUID
    /** 
     * Returns a full representation for a given variant. 
     */

    @Override
    public Representation represent(Variant variant) throws ResourceException {

        Representation representation = null;

        try {
                DBSApis api = new DBSApis();
                File cd = new File();
		//FIXME: WE SHOULD NOT have to set FILE_ID = 0 here
		cd.setFileID(0);
		if (this.lfn!= null) {
			cd.setLogicalFileName( this.lfn );
		} else if ( this.block != null ) {
			cd.setBlockDO(new Block(0, this.block ));
		} else if ( this.dataset != null ) {
			cd.setDatasetDO(new Dataset(0, this.dataset));
		}  
		else {
			//THROW and exception here, want to list all files, thats crazy !
			throw new DBSException("Input Data Error", "You must specify eith an lfn, block or dataset to list files");
		}

                JSONObject retn = api.DBSApiFindFiles(cd);

                representation = new StringRepresentation(
                        retn.toString(), MediaType.TEXT_PLAIN); 

        } catch(Exception ex) {
		System.out.println("Exception raised :" + ex.getMessage() );
		//throw new ResourceException(ex.getMessage());
		throw new ResourceException(100);
        }

        return representation;
    }



    //AA -- POST
    /** 
     * Handle POST requests: create a new item. (insert primrat datasets)
     */
    @Override
    public void acceptRepresentation(Representation entity)
            throws ResourceException {

        try{

		DBSApis api = new DBSApis();

                //Seems like you can only read ONCE from the entity (is it a stream?)
                JSONObject json_req = new JSONObject(entity.getText());
                //System.out.println("json_req:::"+json_req);
                /*  We should put some checks in here
                String primaryDSName = null;
                if (!JSONObject.NULL.equals(json_req.getString("PRIMARY_DS_NAME"))) {
                        primaryDSName =  json_req.getString("PRIMARY_DS_NAME");
                }*/

            	//
		JSONArray inputFiles = json_req.getJSONArray("files");
		for(int i=0; i != inputFiles.length(); ++i ) {
			JSONObject inFile = inputFiles.optJSONObject(i);
			//System.out.println("File Name: "+inFile.getString("LOGICAL_FILE_NAME"));
			File currfile =  new File (0, inFile.getString("LOGICAL_FILE_NAME"), inFile.getInt("IS_FILE_VALID"), new Dataset(0, inFile.getString("DATASET")),
						new Block(0, inFile.getString("BLOCK")), new FileType(1, "EDM"), inFile.getString("CHECK_SUM"), inFile.getInt("EVENT_COUNT"),
						  inFile.getInt("FILE_SIZE"), null, inFile.getString("ADLER32"), inFile.getString("MD5"), inFile.getDouble("AUTO_CROSS_SECTION") 
							,0, "", 0, "");

			//Lets treat the Lumi Sections for this file
			JSONArray inFileLumis = inFile.getJSONArray("FILE_LUMI_LIST");
			//New array to hold lumi sections for this file (currfile)
			JSONArray fls = new JSONArray();
			for(int j=0; j != inFileLumis.length(); ++j ) {
				JSONObject inlumi = inFileLumis.optJSONObject(j);
				System.out.println("		Lumi: " + inlumi.getString("LUMI_SECTION_NUM"));
				fls.put(new FileLumi(0, inlumi.getInt("RUN_NUM"), inlumi.getInt("LUMI_SECTION_NUM"), currfile)); 
			}

			//Now lets do similar with the parents
			JSONArray inFileParents = inFile.getJSONArray("FILE_PARENT_LIST");
			//New array to hold parents for this file (currfile)
			JSONArray fps = new JSONArray();
			for(int k=0; k != inFileParents.length(); ++k ) {
				JSONObject inParent = inFileParents.optJSONObject(k);
				System.out.println("            Parent: " + inParent.getString("FILE_PARENT_LFN"));
				fps.put(new FileParent(0, currfile, new File (0, inParent.getString("FILE_PARENT_LFN"))));
			}

			api.DBSApiInsertFile(currfile, fps, fls);
		}
		

        }catch (DBSException ex){
            System.out.println("DBSException raised :" + ex.getMessage() + ". " + ex.getDetail());
                throw new ResourceException(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST, ex.getMessage() + ". " + ex.getDetail() );
            //response.setEntity(ex.getMessage() + ". " + ex.getDetail(), MediaType.TEXT_PLAIN);

        } catch(Exception ex){
                System.out.println("Exception raised :" + ex );
                throw new ResourceException(org.restlet.data.Status.CLIENT_ERROR_BAD_REQUEST, ex.getMessage() );
        }

    }

}

