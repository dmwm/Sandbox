/*
 *$Id: Element.java,v 1.1 2009/09/21 15:04:39 yuyi Exp $
 * 
 */

package cms.dbs.commons.utils;

import java.util.Hashtable;

/***
 * This class is used by DBSXMLParser to store the key value pairs or Hashtable of key value pairs. 
 * After the parsing is done by the DBSXMLParser a vector of this class is returned for easy retrival 
 * of attributes and values fetched from the xml. This class has three public variables for storing
 * data. 
 * 1) name contains the name of the key. <br>
 * 2) value contains the value of key. <br>
 * 3) attributes contains the hastable of key value pairs. <br>
 *
 */
public class Element{

	public String name;
	public String value;
        public Hashtable attributes;
        
	/**
	* Constructs a Element object that can contain key value pairs or a hastable of key value pairs.
	* @param name The name of the key to be stored in this class.
	*/
	public Element(String name) {
		this.name=name;
		attributes = new Hashtable(); 
	} 

}

