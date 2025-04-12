/**
 * Name: Tianshan Su
 * Student ID: 875734
 */
package exceptions;

/**
 * Port out of range exception
 */
public class PortOutOfRangeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param message the error message
	 */
	public PortOutOfRangeException(String message) {
        super(message);
    }
	
	/**
	 * constructor(empty)
	 */
	public PortOutOfRangeException() {
      
    }
}
