package services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import constants.DictionaryConstant;
import dtos.ServerResponseDTO;
import entities.DictWord;
import server.ServerUI;

/**
 * Main service, provides all the functions needed
 */
public class DictionaryService {
	private String filePath;
	private ConcurrentHashMap<String, List<String>> dictionary=new ConcurrentHashMap<String, List<String>>();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Object fileLock = new Object();
	private static AtomicInteger currentWordsCount = new AtomicInteger(0);
	private ServerUI serverUI;
	
	/**
	 * set server UI
	 * @param serverUI serverUI
	 */
	public void setServerUI(ServerUI serverUI) {
		this.serverUI = serverUI;
	}

	/**
	 * constructor
	 * @param filePath dictionary file path
	 */
	public DictionaryService(String filePath) {
		this.filePath=filePath;
		readDictFromFile();
	}
	
	/**
	 * read the json file into a map
	 */
	private void readDictFromFile() {
		try {
			Reader reader=new FileReader(filePath);
			Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
			Map<String, List<String>> map = gson.fromJson(reader, type);
		    this.dictionary = new ConcurrentHashMap<>(map); 
		    reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}catch(IOException e) {
			System.out.println("Read file failed!");
		}
		
	}
	
	/**
	 * Construct the server response DTO
	 * @param code response code
	 * @param msg response message
	 * @param content response content (list)
	 * @return server response DTO
	 */
	private ServerResponseDTO buildResponse(int code, String msg, List<String> content) {
	    return new ServerResponseDTO(code, msg, content);
	}

	
	/**
	 * Search for a word
	 * @param word the word client wants to search for
	 * @return server response DTO
	 */
	public ServerResponseDTO searchWord(DictWord word) {
		//check whether the word exists
		if(dictionary.containsKey(word.getWord())) {
			System.out.println("Search succeed!");
			return buildResponse(DictionaryConstant.CODE_SUCCESS,DictionaryConstant.SEARCH_SUCCESS,dictionary.get(word.getWord()));
		}else {
			System.out.println("Search failed!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.WORD_NOT_EXIST,null);
		}
	}
	
	public void initiateWordsCount() {
		currentWordsCount.set(dictionary.size());
	    serverUI.updateWordsCount(currentWordsCount.get());
	}
	
	
	/**
	 * Add a new word
	 * @param word the new word
	 * @return server response DTO
	 */
	public ServerResponseDTO addWord(DictWord word) {
		//check whether the word exists, only can add the word and the meanings if the word does not exist
		if(dictionary.containsKey(word.getWord())) {
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.WORD_ALREADY_EXIST,null);
		}
		
		//add the word and the meaning to dictionary
		String meaning=word.getMeanings();
		List<String> meaningsList = Arrays.asList(meaning.split("\\n")); //split meanings by line breaks
        dictionary.put(word.getWord(), meaningsList);
		
        //try to write the new dictionary to file
		try{
			synchronized (fileLock) {
				FileWriter writer=new FileWriter(filePath);
				gson.toJson(dictionary, writer);
				writer.flush();
	            writer.close();
		        System.out.println("Word add succeed!");
		        currentWordsCount.incrementAndGet();//increase word count and update it on server UI
		        serverUI.updateWordsCount(currentWordsCount.get());
		        serverUI.refreshWords();//refresh the word list on server UI
				return buildResponse(DictionaryConstant.CODE_SUCCESS,DictionaryConstant.ADD_WORD_SUCCESS,null);
			}
		}catch (IOException e) {
			System.out.println("Word add failed");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.ADD_WORD_FAILED,null);
	    }
		
	}
	
	/**
	 * Delete a word in dictionary
	 * @param word the word to be deleted
	 * @return server response DTO
	 */
	public ServerResponseDTO deleteWord(DictWord word) {
		//check whether the word exists, only can delete the word if the word exist in dictionary
		if(!dictionary.containsKey(word.getWord())) {
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.WORD_NOT_EXIST,null);
		}
		
		//try to delete the word and the meaning
		dictionary.remove(word.getWord());
		try{
			synchronized (fileLock) {
				FileWriter writer=new FileWriter(filePath);
				gson.toJson(dictionary, writer);
				writer.flush();
	            writer.close();
		        System.out.println("Word delete succeed!");
		        currentWordsCount.decrementAndGet();//decrease word count and update it on server UI
		        serverUI.updateWordsCount(currentWordsCount.get());
		        serverUI.refreshWords();//refresh the word list on server UI
				return buildResponse(DictionaryConstant.CODE_SUCCESS,DictionaryConstant.DELETE_WORD_SUCCESS,null);
			}
		}catch (IOException e) {
			System.out.println("Word delete failed!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.DELETE_WORD_FAILED,null);
	    }
	}
	
	/**
	 * Add a new meaning to a existing word
	 * @param word the word to add a new meaning
	 * @return server response DTO
	 */
	public ServerResponseDTO addMeaning(DictWord word) {
		//check whether the word exists, only can add meaning if the word exist in dictionary
		if(!dictionary.containsKey(word.getWord())) {
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.WORD_NOT_EXIST,null);
		}
		
		//check if the meaning already exist (case insensitive)
		String newMeaningLC=word.getNewMeaning().toLowerCase();
		List<String> meaningList = new ArrayList<>(dictionary.get(word.getWord())); //make a new list to add the new meaning
		
		//transfer all meanings in dictionary to lower case
		List<String> meaningListLC = new ArrayList<>();
		for (String meaning : meaningList) {
			meaningListLC.add(meaning.toLowerCase());
		}
		//if the meaning already exists, return with a failed code
		if(meaningListLC.contains(newMeaningLC)) {
			System.out.println("Meaning add failed: The meaning already exists!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.MEANING_ALREADY_EXIST,null);
		}
		
		//try to add the meaning
		meaningList.add(word.getNewMeaning());
		dictionary.put(word.getWord(), meaningList);
		try{
			synchronized (fileLock) {
				FileWriter writer=new FileWriter(filePath);
				gson.toJson(dictionary, writer);
				writer.flush();
	            writer.close();
		        System.out.println("Meaning add succeed!");
				return buildResponse(DictionaryConstant.CODE_SUCCESS,DictionaryConstant.ADD_MEANING_SUCCESS,null);
			}
		}catch (IOException e) {
			System.out.println("Meaning add failed!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.ADD_MEANING_FAILED,null);
	    }
	}

	/**
	 * Update meaning
	 * @param word the word to be updated
	 * @return server response DTO
	 */
	public ServerResponseDTO updateMeaning(DictWord word) {
		//check whether the word exists, only can update the meaning if the word exist in dictionary
		if(!dictionary.containsKey(word.getWord())) {
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.WORD_NOT_EXIST,null);
		}
		
		//check whether the old meaning exists(case insensitive)
		String oldMeaningLC=word.getOldMeaning().toLowerCase();
		List<String> meaningList = new ArrayList<>(dictionary.get(word.getWord())); //make a new list to update the meaning
		
		//transfer all meanings in dictionary to lower case
		List<String> meaningListLC = new ArrayList<>();
		for (String meaning : meaningList) {
			meaningListLC.add(meaning.toLowerCase());
		}
		//if the old meaning does not exist, return with a failed code
		if(!meaningListLC.contains(oldMeaningLC)) {
			System.out.println("Meaning update failed: The old meaning does not exist in the dictionary!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.MEANING_DOES_NOT_EXIST,null);
		}
		
		//check whether the new meaning is the same as the old meaning(case insensitive)
		String newMeaningLC=word.getNewMeaning().toLowerCase();
		if(meaningListLC.contains(newMeaningLC)) {
			System.out.println("Meaning update failed: The new meaning cannot be the same as old meaning in dictionary!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.MEANING_INVALID,null);
		}
		
		//update the meaning
		int indexToUpdate = meaningListLC.indexOf(oldMeaningLC);
	    dictionary.get(word.getWord()).set(indexToUpdate, word.getNewMeaning());
	    try{
	    	synchronized (fileLock) {
	    		FileWriter writer=new FileWriter(filePath);
				gson.toJson(dictionary, writer);
				writer.flush();
	            writer.close();
		        System.out.println("Meaning update succeed!");
				return buildResponse(DictionaryConstant.CODE_SUCCESS,DictionaryConstant.UPDATE_MEANING_SUCCESS,null);
			}
		}catch (IOException e) {
			System.out.println("Meaning update failed!");
			return buildResponse(DictionaryConstant.CODE_FAILED,DictionaryConstant.UPDATE_MEANING_FAILED,null);
	    }
	}
	
	/**
	 * display all words in server UI
	 * @return all words in dictionary
	 */
	public Set<String> getAllWords() {
	    return dictionary.keySet();
	}
	
}
