package constants;

/**
 * Dictionary Constants
 */
public class DictionaryConstant {
	/** Status code indicating success. */
    public static int CODE_SUCCESS = 0;

    /** Status code indicating failure. */
    public static int CODE_FAILED = 1;
    

    /** Text label for the 'Search' button. */
    public static String TEXT_SEARCH = "Search";

    /** Text label for the 'Add Word' button. */
    public static String TEXT_ADD_WORD = "Add";

    /** Text label for the 'Delete Word' button. */
    public static String TEXT_DELETE = "Delete";

    /** Text label for the 'Add Meaning' button. */
    public static String TEXT_ADD_MEANING = "Add";

    /** Text label for the 'Update Meaning' button. */
    public static String TEXT_UPDATE_MEANING = "Update";

    /** Error message for unknown operation. */
    public static String UNKNOWN_OPERATION = "Failed: Unknown operation, please try again!";

    /** Error message for unknown error. */
    public static String UNKNOWN_ERROR = "Failed: Unknown error, please try again!";

    /** Error message when the word field is empty. */
    public static String WORD_CANNOT_BE_EMPTY = "Failed: The word cannot be empty!";

    /** Error message when the word is not found in the dictionary. */
    public static String WORD_NOT_EXIST = "Failed: This word does not exist in the dictionary!";

    /** Error message when the word already exists in the dictionary. */
    public static String WORD_ALREADY_EXIST = "Failed: This word is already in the dictionary!";

    /** Error message when the meaning already exists in the dictionary. */
    public static String MEANING_ALREADY_EXIST = "Failed: This meaning is already in the dictionary!";

    /** Error message when the old meaning does not exist in the dictionary. */
    public static String MEANING_DOES_NOT_EXIST = "Failed: This old meaning does not exist in the dictionary!";

    /** Error message when the meaning field is empty. */
    public static String MEANING_CANNOT_BE_EMPTY = "Failed: The meaning cannot be empty!";

    /** Error message when the old and new meanings are the same. */
    public static String MEANING_CANNOT_BE_SAME = "Failed: The old and new meaning input cannot be the same!";

    /** Error message when the new meaning is the same as an existing meaning. */
    public static String MEANING_INVALID = "Failed: The new meaning cannot be the same as the old meaning that already exists in the dictionary!";

    /** Success message for a successful word search. */
    public static String SEARCH_SUCCESS = "Succeed: The search succeeded!";

    /** Success message for adding a word. */
    public static String ADD_WORD_SUCCESS = "Succeed: The word is successfully added to the dictionary!";

    /** Failure message for failing to add a word. */
    public static String ADD_WORD_FAILED = "Failed: The word cannot be added, please try again!";

    /** Success message for deleting a word. */
    public static String DELETE_WORD_SUCCESS = "Succeed: The word is successfully deleted!";

    /** Failure message for failing to delete a word. */
    public static String DELETE_WORD_FAILED = "Failed: The word cannot be deleted, please try again!";

    /** Success message for adding a meaning. */
    public static String ADD_MEANING_SUCCESS = "Succeed: The meaning is successfully added!";

    /** Failure message for failing to add a meaning. */
    public static String ADD_MEANING_FAILED = "Failed: The meaning cannot be added, please try again!";

    /** Success message for updating a meaning. */
    public static String UPDATE_MEANING_SUCCESS = "Succeed: The meaning is successfully updated!";

    /** Failure message for failing to update a meaning. */
    public static String UPDATE_MEANING_FAILED = "Failed: The meaning cannot be updated, please try again!";
}
