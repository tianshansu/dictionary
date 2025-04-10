package dtos;


import enums.OperationType;

/**
 * The client request DTO
 */
public class ClientRequestDTO {
	private OperationType operationType;
	private String word;
	
	private String meanings;
	private String oldMeaning;
	private String newMeaning;
	
	/**
	 * constructor (empty)
	 */
	public ClientRequestDTO() {
		
	}
	
	
	//getters and setters
	/**
	 * get operation type
	 * @return operation type
	 */
	public OperationType getOperationType() {
		return operationType;
	}
	
	/**
	 * set operation type
	 * @param operationType operation type
	 */
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	
	/**
	 * get word
	 * @return word(string)
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * set word
	 * @param word word(string)
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * get the old meaning
	 * @return old meaning(string)
	 */
	public String getOldMeaning() {
		return oldMeaning;
	}
	
	/**
	 * set old meaning
	 * @param oldMeaning old meaning(string)
	 */
	public void setOldMeaning(String oldMeaning) {
		this.oldMeaning = oldMeaning;
	}
	
	/**
	 * get new meaning
	 * @return new meaning(string)
	 */
	public String getNewMeaning() {
		return newMeaning;
	}
	
	/**
	 * set new meaning
	 * @param newMeaning new meaning(string)
	 */
	public void setNewMeaning(String newMeaning) {
		this.newMeaning = newMeaning;
	}
	
	/**
	 * get meanings
	 * @return meanings(string)
	 */
	public String getMeanings() {
		return meanings;
	}
	
	/**
	 * set meanings
	 * @param meanings meanings(string)
	 */
	public void setMeanings(String meanings) {
		this.meanings = meanings;
	}
	
	/**
	 * to string method
	 */
	@Override
	public String toString() {
		return "ClientRequestDTO [operationType=" + operationType + ", word=" + word + ", meanings=" + meanings
				+ ", oldMeaning=" + oldMeaning + ", newMeaning=" + newMeaning + ", toString()=" + super.toString()
				+ "]";
	}
	
}
