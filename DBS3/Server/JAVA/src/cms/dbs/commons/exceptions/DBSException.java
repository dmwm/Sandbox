/**
 * 
 $Revision: 1.2 $"
 $Id: DBSException.java,v 1.2 2009/08/31 20:01:08 afaq Exp $"
 *
 * A thin wrapper around <code>java.lang.Exception</code> 
*/

package cms.dbs.commons.exceptions;

public class DBSException extends Exception{
	private String detail;

	/**
	* Constructs a DBSException object similar to its parent Exception class
	* @param message The exception message in string format to be stored in this DBSException class
	*/
	public DBSException(String message) {
		super(message);
	}

	/**
	* Constructs a DBSException object 
	* @param category This will describe the type of message (for example Database Error, Internal Error, parse Error, JSON Error etc).
	* @param detail The exception verbose detail in string format to be stored in this DBSException class
	*/
	public DBSException(String message, String detail) {
		super(message);
		this.detail = detail;
	}

	/**
	* Returns the error verbose detail of this throwable object.
	* @return 
	* the error verbose detail of this <code>Throwable</code> object if it was created with an error detail; or null if it was created with no error detail.
	*/
	public String getDetail() {
		return detail;
	}

}
