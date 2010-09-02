/***
 * $Id: PingDBS.java,v 1.2 2009/11/06 22:34:57 afaq Exp $
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

import cms.dbs.commons.exceptions.DBSException;
import org.restlet.data.Status;

/** 
 * Resource which has only one representation. 
 * 
 */  
public class PingDBS extends Resource {  
  
    public PingDBS (Context context, Request request,  
            Response response) {  
        super(context, request, response);  

        // This representation has only one type of representation.  
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));  
    }  
  

    //GET
    /** 
     * Returns a full representation for a given variant. 
     */  
    @Override  
    public Representation represent(Variant variant) throws ResourceException {  

	Representation representation = null;
	try{	
		JSONObject jj=new JSONObject();
		jj.put("DBS_ALIVE", 1);

        	representation = new StringRepresentation(  
               		jj.toString(), MediaType.TEXT_PLAIN);  
        } catch(Exception ex){
            System.out.println("Exception raised :" + ex.getMessage() + ". " );
        }
	
        return representation;  
    }  


}  
