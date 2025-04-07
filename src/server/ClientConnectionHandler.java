package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.spi.AbstractResourceBundleProvider;

import com.google.gson.Gson;

import constants.DictionaryConstant;
import dtos.ClientRequestDTO;
import dtos.ServerResponseDTO;
import entities.DictWord;
import enums.OperationType;
import services.DictionaryService;

/**
 * Client Connection, server send and receive data from the clients
 */
public class ClientConnectionHandler implements Runnable {
	DataInputStream is;
	DataOutputStream os;
	Socket clientSocket;// get client socket to send and receive data from the server
	Gson gson = new Gson();
	DictionaryService dictionaryService;

	public ClientConnectionHandler(Socket socket) {
		this.clientSocket = socket;
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	// implement the run method
	@Override
	public void run() {
		System.out.println(" this client connection is running ... ");

		// keep reading the information send by the client

		try {
			while (true) {
				//read client's message from channel and transfer Json to ClientRequestDTO class
				String clientRequest = is.readUTF();
				ClientRequestDTO clientRequestDTO = gson.fromJson(clientRequest, ClientRequestDTO.class);
				System.out.println("server received the request:" + clientRequestDTO.toString());
				
				//put all data into a DictWord obj - used for communicate with service
				DictWord word = new DictWord();
				word.setWord(clientRequestDTO.getWord());
				word.setMeanings(clientRequestDTO.getMeanings());
				word.setOldMeaning(clientRequestDTO.getOldMeaning());
				word.setNewMeaning(clientRequestDTO.getOldMeaning());

				ServerResponseDTO serverResponseDTO;
				switch (clientRequestDTO.getOperationType()) {
					case SEARCH_WORD: {
						// if the client wants to search a word, call searchWord method in service
						serverResponseDTO = dictionaryService.searchWord(word);
						
						break;
					}
					case ADD_WORD:{
						// if the client wants to add a word, call add word method in service
						serverResponseDTO=dictionaryService.addWord(word);
						break;
						
					}
					default: {
						serverResponseDTO=new ServerResponseDTO(DictionaryConstant.CODE_FAILED, DictionaryConstant.UNKNOWN_OPERATION, null);
						break;
					}
				}
				
				String serverResponseDTOJson = gson.toJson(serverResponseDTO);
				os.writeUTF(serverResponseDTOJson);
			}

		} catch (SocketException socketException) {
			System.out.println("Client disconnected: " + socketException.getMessage());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				// close all resources
				is.close();
				os.close();
				clientSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

	}

}
