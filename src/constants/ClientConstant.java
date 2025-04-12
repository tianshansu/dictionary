/**
 * Name: Tianshan Su
 * Student ID: 875734
 */
package constants;

/**
 * Client Constants
 */
public class ClientConstant {
	/** Error message for when the port number is not a valid number. */
    public static String PORT_NUMBER_INCORRECT_FORMAT = "The port number is in a incorrect format!";

    /** Error message for when the port number is not within the valid range (1024-65535). */
    public static String PORT_NUMBER_INCORRECT_RANGE = "The port number is out of range!";

    /** Error message for when the number of command-line arguments is incorrect. */
    public static String ARGUMENT_INCORRECT_LENGTH = "The arguments are in incorrect length!";

    /** Error message for when the server address is not a valid IP or host name. */
    public static String SERVER_ADDRESS_INCORRECT = "The server address is incorrect!";

    /** Error message for when the server address cannot be connected. */
    public static String SERVER_ADDRESS_UNAVAILABLE = "The server address is unavailable!";
}
