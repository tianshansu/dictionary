package exceptions;

public class PortOutOfRangeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PortOutOfRangeException(String message) {
        super(message);
    }
	
	public PortOutOfRangeException() {
      
    }
}
