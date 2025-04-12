/**
 * Name: Tianshan Su
 * Student ID: 875734
 */
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
	/**
	 * get word
	 * @return the word(string)
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
	 * get old meaning
	 * @return the old meaning(string)
	 */
	public String getOldMeaning() {
		return oldMeaning;
	}
	
	/**
	 * set old meaning 
	 * @param oldMeaning the old meaning(string)
	 */
	public void setOldMeaning(String oldMeaning) {
		this.oldMeaning = oldMeaning;
	}
	
	/**
	 * get new meaning
	 * @return the new meaning(string)
	 */
	public String getNewMeaning() {
		return newMeaning;
	}
	
	/**
	 * set new meaning
	 * @param newMeaning the new meaning
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
	
	
}
