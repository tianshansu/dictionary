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
	
	public ClientRequestDTO() {
		
	}
	
	
	//getters and setters
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getOldMeaning() {
		return oldMeaning;
	}
	public void setOldMeaning(String oldMeaning) {
		this.oldMeaning = oldMeaning;
	}
	public String getNewMeaning() {
		return newMeaning;
	}
	public void setNewMeaning(String newMeaning) {
		this.newMeaning = newMeaning;
	}
	public String getMeanings() {
		return meanings;
	}
	public void setMeanings(String meanings) {
		this.meanings = meanings;
	}
	
	@Override
	public String toString() {
		return "ClientRequestDTO [operationType=" + operationType + ", word=" + word + ", meanings=" + meanings
				+ ", oldMeaning=" + oldMeaning + ", newMeaning=" + newMeaning + ", toString()=" + super.toString()
				+ "]";
	}
	
}
