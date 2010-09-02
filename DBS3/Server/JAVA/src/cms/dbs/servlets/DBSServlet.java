/*i*
 * $Id: DBSServlet.java,v 1.12 2009/11/09 21:54:15 afaq Exp $
 *
 **/
package cms.dbs.servlets;

//Adding REST interface
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Router;

import org.restlet.Context;

import cms.dbs.commons.utils.DBSSrvcConfig;

//The APIs
import cms.dbs.apis.PingDBS;
import cms.dbs.apis.PrimaryDatasets;
import cms.dbs.apis.Datasets;
import cms.dbs.apis.Blocks;
import cms.dbs.apis.Files;

/**
* servlet config is described in etc/web.xml.
*/

public class DBSServlet extends Application {
    /** 
     * Creates a root Restlet that will receive all incoming calls. 

	THIS is called ONLY ONCE

     */
    @Override
    public Restlet createRoot() {
        	// Create a router Restlet that routes each call to a  
        	Router router = new Router(getContext());

    		try {

			/**
			// AA - failed attempts to get to serverlet context !
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        		//ServletContextAdapter srvltContextAdapter = new ServletContextAdapter(this, getContext());
        		//ServletContextAdapter srvltContext = new ServletContextAdapter(this, getContext());
        		//ServletContext srvltContext = srvltContextAdapter.getServletContext();
			ServletContext srvltContext = (ServletContext) 
					getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
        		String configFilePath = srvltContext.getRealPath("META-INF/context.xml");
	
        		System.out.println("configFilePath: "+configFilePath);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        		Context ctx = getContext();

			Series<Parameter> params= ctx.getParameters();
			System.out.println("params::::::"+params.toString());
			Set<String> param_names = params.getNames();
			Iterator iterator = param_names.iterator();
			while( iterator.hasNext() ){
				System.out.println( iterator.next() );
			}

			*/

			// AA- For now I am hard coding servlet context
			String configFilePath ="/home/anzar/devDBS3/DBS/DBS3/Server/JAVA/etc/context.xml";
			DBSSrvcConfig dbsconfig = DBSSrvcConfig.getInstance(configFilePath);

			// Define a Default page for DBS, just a PING at the moment, we can return server status information
			router.attachDefault(PingDBS.class);

			// Define route to PrimaryDatasets
			router.attach("/primarydatasets", PrimaryDatasets.class);

			// Define route to Datasets
			router.attach("/datasets", Datasets.class);
			
			// Define route to Blocks
			router.attach("/blocks", Blocks.class);

			// Define route to Files
			router.attach("/files", Files.class);


			/** Already obsoltete scheme
		        // Define route to PrimaryDatasets
        		router.attach("/PrimaryDatasets/", PrimaryDatasets.class);
		        // Define route to PrimaryDatasets, with name specified (in this case its going to same class
        		router.attach("/PrimaryDatasets/{PRIMARY_DATASET_NAME}", PrimaryDatasets.class);

			// Define route to Datasets
			router.attach("/Datasets/", Datasets.class);
			router.attach("/Datasets/{PRIMARY_DATASET_NAME}", Datasets.class);
			router.attach("/Datasets/{PRIMARY_DATASET_NAME}/{PROCESSED_DATASET_NAME}", Datasets.class);
			router.attach("/Datasets/{PRIMARY_DATASET_NAME}/{PROCESSED_DATASET_NAME}/{DATA_TIER}", Datasets.class);

			// Define route to Blocks
			//WE may not need all the block so this route may not be required anyways
			router.attach("/Blocks/", Blocks.class);
			//DATA_TIER_GUID in this case will contain DATA_TIER#GUID part of Block name
			router.attach("/Blocks/{PRIMARY_DATASET_NAME}/{PROCESSED_DATASET_NAME}/{DATA_TIER_GUID}", Blocks.class);
			**/
			System.out.println("DBS READY");

    		} catch(Exception e) {
			System.out.println("DBS Failed to initialize: "+e.getMessage());
			e.printStackTrace();
    		}
        return router;
	}
	private String supportedSchemaVersion;
	private String supportedClientVersions;
}
