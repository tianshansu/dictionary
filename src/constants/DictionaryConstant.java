package constants;

public class DictionaryConstant {
	public static int CODE_SUCCESS=0;
	public static int CODE_FAILED=1;
	
	public static String TEXT_SEARCH="Search";
	public static String TEXT_ADD_WORD="Add";
	public static String TEXT_DELETE="Delete";
	public static String TEXT_ADD_MEANING="Add";
	public static String TEXT_UPDATE_MEANING="Update";
	
	
	public static String UNKNOWN_OPERATION="Failed: Unknown operation, please try again!";
	public static String UNKNOWN_ERROR="Failed: Unknown error, please try again!";
	
	public static String WORD_CANNOT_BE_EMPTY="Failed: The word cannot be empty!";
	public static String WORD_NOT_EXIST="Failed: This word does not exist in the dictionary!";
	public static String WORD_ALREADY_EXIST="Failed: This word is already in the dictionary!";
	public static String MEANING_ALREADY_EXIST="Failed: This meaning is already in the dictionary!";
	public static String MEANING_DOES_NOT_EXIST="Failed: This old meaning does not exist in the dictionary!";
	public static String MEANING_CANNOT_BE_EMPTY="Failed: The meaning cannot be empty!";
	public static String MEANING_CANNOT_BE_SAME="Failed: The old and new meaning input cannot be the same!";
	public static String MEANING_INVALID="Failed: The new meaning cannot be the same as the old meaning that already exists in the dictionary!";
	
	
	public static String SEARCH_SUCCESS="Succeed: The search succeeded!";
	public static String ADD_WORD_SUCCESS="Succeed: The word is successfully added to the dictionary!";
	public static String ADD_WORD_FAILED="Failed: The word cannot be added, please try again!";
	public static String DELETE_WORD_SUCCESS="Succeed: The word is successfully deleted!";
	public static String DELETE_WORD_FAILED="Failed: The word cannot be deleted, please try again!";
	public static String ADD_MEANING_SUCCESS="Succeed: The meaning is successfully added!";
	public static String ADD_MEANING_FAILED="Failed: The meaning cannot be added, please try again!";
	public static String UPDATE_MEANING_SUCCESS="Succeed: The meaning is successfully updated!";
	public static String UPDATE_MEANING_FAILED="Failed: The meaning cannot be updated, please try again!";
}
