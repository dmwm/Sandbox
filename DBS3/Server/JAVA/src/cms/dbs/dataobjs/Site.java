/**
 * 
 $Revision: 1.5 $"
 $Id: Site.java,v 1.5 2009/10/06 20:22:18 afaq Exp $"
 *
 * Data Object from table : SITES
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class Site extends JSONObject  {

	public Site ( ) {

	}
		
        public Site ( int siteID, String siteName ) throws Exception  {
		
                this.putOnce("SITE_ID", siteID );
                this.putOnce("SITE_NAME", siteName );
        }

	public void setSiteID (int siteID) throws Exception {
 		this.put( "SITE_ID", siteID );
	}
	
	public void setSiteName (String siteName) throws Exception {
 		this.put( "SITE_NAME", siteName );
	}
	
	int getSiteID ( )  throws Exception {
		int siteID = 0;
               	if (!JSONObject.NULL.equals(this.get("SITE_ID"))) {
                       	siteID = this.getInt("SITE_ID");
               	}
                return siteID;
        }
	
	String getSiteName ( )  throws Exception {
		String siteName = null;
               	if (!JSONObject.NULL.equals(this.get("SITE_NAME"))) {
                       	siteName = this.getString("SITE_NAME");
               	}
                return siteName;
        }
	
}