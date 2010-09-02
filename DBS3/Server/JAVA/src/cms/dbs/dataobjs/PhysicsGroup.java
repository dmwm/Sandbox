/**
 * 
 $Revision: 1.6 $"
 $Id: PhysicsGroup.java,v 1.6 2009/10/13 15:37:31 yuyi Exp $"
 *
 * Data Object from table : PHYSICS_GROUPS
*/

package cms.dbs.dataobjs;

import org.json.JSONObject;

public class PhysicsGroup extends JSONObject  {

	public PhysicsGroup ( ) {

	}
		
        public PhysicsGroup ( int physicsGroupID, String physicsGroupName, String physicsGroupConvener ) throws Exception  {
		
                this.putOnce("PHYSICS_GROUP_ID", physicsGroupID );
                this.putOnce("PHYSICS_GROUP_NAME", physicsGroupName );
                this.putOnce("PHYSICS_GROUP_CONVENER", physicsGroupConvener );
        }

	public PhysicsGroup ( int physicsGroupID, String physicsGroupName) throws Exception  {

	    this.putOnce("PHYSICS_GROUP_ID", physicsGroupID );
	    this.putOnce("PHYSICS_GROUP_NAME", physicsGroupName );
	}

	public void setPhysicsGroupID (int physicsGroupID) throws Exception {
 		this.put( "PHYSICS_GROUP_ID", physicsGroupID );
	}
	
	public void setPhysicsGroupName (String physicsGroupName) throws Exception {
 		this.put( "PHYSICS_GROUP_NAME", physicsGroupName );
	}
	
	public void setPhysicsGroupConvener (String physicsGroupConvener) throws Exception {
 		this.put( "PHYSICS_GROUP_CONVENER", physicsGroupConvener );
	}
	
	int getPhysicsGroupID ( )  throws Exception {
		int physicsGroupID = 0;
               	if (!JSONObject.NULL.equals(this.get("PHYSICS_GROUP_ID"))) {
                       	physicsGroupID = this.getInt("PHYSICS_GROUP_ID");
               	}
                return physicsGroupID;
        }
	
	String getPhysicsGroupName ( )  throws Exception {
		String physicsGroupName = null;
               	if (!JSONObject.NULL.equals(this.get("PHYSICS_GROUP_NAME"))) {
                       	physicsGroupName = this.getString("PHYSICS_GROUP_NAME");
               	}
                return physicsGroupName;
        }
	
	String getPhysicsGroupConvener ( )  throws Exception {
		String physicsGroupConvener = null;
               	if (!JSONObject.NULL.equals(this.get("PHYSICS_GROUP_CONVENER"))) {
                       	physicsGroupConvener = this.getString("PHYSICS_GROUP_CONVENER");
               	}
                return physicsGroupConvener;
        }
	
}
