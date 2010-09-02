/***
 * $Id: Blocks.java,v 1.5 2009/11/30 22:08:57 afaq Exp $
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

import cms.dbs.apis.DBSApis;
import cms.dbs.commons.exceptions.DBSException;
import org.restlet.data.Status;

import cms.dbs.dataobjs.Block;

import cms.dbs.commons.utils.DBSSrvcUtil;

public class Blocks extends Resource {

    String blockName;

    public Blocks(Context context, Request request, Response response) {

        super(context, request, response);

        String req=(String) request.getResourceRef().toString();
        java.util.HashMap kvalues = DBSSrvcUtil.getUrlParams(req);	
	this.blockName = (String)kvalues.get("block");

        //FIXME: We should check if complete path is provided here or NOT

         // Allow modifications of this resource via POST/PUT/DELETE requests.  
         setModifiable(true);

        // This resource has only one type of representation.  
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }

    //GET  http://.../Blocks/
    /** 
     * Returns a full representation for a given variant. 
     */

    @Override
    public Representation represent(Variant variant) throws ResourceException {

        Representation representation = null;

        try {
                DBSApis api = new DBSApis();
		Block bk = new Block();	
		//FIXME: we should have to do following
		bk.setBlockID(0);

		if (this.blockName!= null) {
			bk.setBlockName ( this.blockName ) ;
		}
		else {
			throw new DBSException ("Incorrect parameter", "Cannot list all block, please specify a parameter");
		}

                JSONObject retn = api.DBSApiFindBlocks(bk);

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
		//Seems like you can only read ONCE from the entity (is it a stream?)
                JSONObject json_req = new JSONObject(entity.getText());
		//System.out.println("json_req:::"+json_req);

		Block bki = new Block(0, json_req.getString("BLOCK_NAME"));
                bki.setCreateBy("web-client");
                bki.setCreationDate(0);

		DBSApis api = new DBSApis();	
            	api.DBSApiInsertBlock(bki);

        } catch(Exception ex){
            	System.out.println("Exception raised :" + ex.getMessage() + ". " );
        }
    }
}

