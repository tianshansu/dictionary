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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import constants.DictionaryConstant;
import dtos.ServerResponseDTO;
import entities.DictWord;

public class DictionaryService {
	private String filePath;
	private Map<String, List<String>> dictionary;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	//constructor
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
			this.dictionary=gson.fromJson(reader, type);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * search for a word
	 * @param word the word client wants to search for
	 * @return server response DTO
	 */
	public ServerResponseDTO searchWord(DictWord word) {
		List<String> list=new ArrayList<>();
		int code;
		String msg=null;
		//check whether the word exists
		if(dictionary.containsKey(word.getWord())) {
			list=dictionary.get(word.getWord());
			msg=DictionaryConstant.SEARCH_SUCCESS;
			code=DictionaryConstant.CODE_SUCCESS;
			System.out.println("Search successful!");
		}else {
			msg=DictionaryConstant.WORD_NOT_EXIST;
			code=DictionaryConstant.CODE_FAILED;
		}
		
		ServerResponseDTO serverResponseDTO=new ServerResponseDTO(code,msg, list);
		return serverResponseDTO;
	}
	
	public ServerResponseDTO addWord(DictWord word) {
		int code;
		String msg=null;
		//check whether the word exists, only can add the word and the meanings if the word does not exist
		if(dictionary.containsKey(word.getWord())) {
			msg=DictionaryConstant.WORD_ALREADY_EXIST;
			code=DictionaryConstant.CODE_FAILED;
		}else {
			//add the word and the meaning to dictionary
			String meaning=word.getMeanings();
			List<String> meaningsList = Arrays.asList(meaning.split("\\n")); //split meanings by line breaks
	        dictionary.put(word.getWord(), meaningsList);
			
			try{
				FileWriter writer=new FileWriter(filePath);
				gson.toJson(dictionary, writer);
				writer.flush();
	            writer.close();
		        System.out.println("Write successful!");
		        
		        msg=DictionaryConstant.ADD_WORD_SUCCESS;
				code=DictionaryConstant.CODE_SUCCESS;
				
			}catch (IOException e) {
				System.out.println("Write failed");
				msg=DictionaryConstant.ADD_WORD_FAILED;
				code=DictionaryConstant.CODE_FAILED;
		    }
		}
		ServerResponseDTO serverResponseDTO=new ServerResponseDTO(code,msg, null);
		System.out.println(serverResponseDTO.toString());
		return serverResponseDTO;
	}
}
