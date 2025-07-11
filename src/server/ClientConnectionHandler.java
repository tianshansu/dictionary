/**
 * Name: Tianshan Su
 * Student ID: 875734
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import com.google.gson.Gson;
import constants.DictionaryConstant;
import dtos.ClientRequestDTO;
import dtos.ServerResponseDTO;
import entities.DictWord;
import services.DictionaryService;

/**
 * Client Connection, server send and receive data from the clients
 */
public class ClientConnectionHandler implements Runnable {
	private DataInputStream is;
	private DataOutputStream os;
	private Socket clientSocket;// get client socket to send and receive data from the server
	private Gson gson = new Gson();
	private DictionaryService dictionaryService;
	private ServerUI serverUI;
	private String userId;

	/**
	 * get client socket
	 * @return clientSocket
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	/**
	 * set client socket
	 * @param clientSocket clientSocket
	 */
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * set server UI
	 * @param serverUI serverUI
	 */
	public void setServerUI(ServerUI serverUI) {
		this.serverUI = serverUI;
	}
	
	/**
	 * get data output stream
	 * @return output stream
	 */
	public DataOutputStream getOutputStream() {
	    return os;
	}

	/**
	 * Constructor
	 * @param socket client socket
	 * @param userId current user id
	 */
	public ClientConnectionHandler(Socket socket,String userId) {
		this.clientSocket = socket;
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
			this.userId=userId;
		} catch (IOException ioException) {
			System.out.println("IOException occurred: " + ioException.getMessage());
		}
	}

	/**
	 * set dictionary service
	 * @param dictionaryService dictionaryService
	 */
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	// implement the run method
	@Override
	public void run() {
		System.out.println(" this client connection is running ... ");

		// keep reading the information send by the client

		try {
			sendUserIdToClient();//send current user ID to client
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
				word.setNewMeaning(clientRequestDTO.getNewMeaning());

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
					case DELETE_WORD:{
						// if the client wants to delete a word, call delete word method in service
						serverResponseDTO=dictionaryService.deleteWord(word);
						break;
						
					}
					case ADD_MEANING:{
						// if the client wants to add a new meaning to a existing word, call add meaning method in service
						serverResponseDTO=dictionaryService.addMeaning(word);
						break;
					}
					case UPDATE_MEANING:{
						// if the client wants to update an existing meaning to a existing word, call update meaning method in service
						serverResponseDTO=dictionaryService.updateMeaning(word);
						break;
					}
					default: {
						serverResponseDTO=new ServerResponseDTO(DictionaryConstant.CODE_FAILED, DictionaryConstant.UNKNOWN_OPERATION, null);
						break;
					}
				}
				
				String serverResponseDTOJson = gson.toJson(serverResponseDTO);
				
				//add a lock when writing data in output stream
				synchronized (os) {
				    os.writeUTF(serverResponseDTOJson);
				}
			}

		} catch (SocketException socketException) {
			System.out.println("Client disconnected: " + socketException.getMessage());
			Server.currentUserCount.decrementAndGet();//decrease user count
			serverUI.updateUserCount(Server.currentUserCount.get());
			
			Server.removeConnection(userId,true);
		} catch (IOException ioException) {
			System.out.println("IOException occurred: " + ioException.getMessage());
		} finally {
			try {
				// close all resources
				is.close();
				os.close();
				clientSocket.close();

			} catch (IOException ioException) {
				System.out.println("Resource close failed"+ioException.getMessage());
			}
		}

	}
	
	private void sendUserIdToClient() throws IOException {
		synchronized (os) {
			os.writeUTF(String.valueOf(userId));
		}
	}
	
	

}
