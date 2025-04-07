package entities;


/**
 * Word entity used in server-side dictionary operations
 */
public class DictWord {
	private String word;
	private String meanings;
	private String oldMeaning;
	private String newMeaning;
	
	//getters and setters
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
	
	
}
