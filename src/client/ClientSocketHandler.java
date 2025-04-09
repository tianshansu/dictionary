package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import dtos.ClientRequestDTO;
import dtos.ServerResponseDTO;

/**
 * Client communicate with the server, client send and receive data from the server
 */
public class ClientSocketHandler implements Runnable {
	private Socket clientSocket;
	private ClientUI clientUI;
	private DataInputStream is;
	private DataOutputStream os;
	private Gson gson = new Gson();

	/**
	 * constructor
	 * @param clientSocket clientSocket
	 */
	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("IOException occurred: " + e.getMessage());
		}
	}

	/**
	 * set client UI
	 * @param clientUI clientUI
	 */
	public void setClientUI(ClientUI clientUI) {
		this.clientUI = clientUI;
	}


	@Override
	public void run() {
		System.out.println(" this client socket handler is running ... ");
		// keep reading the information send by the server
		try {
			//display current user ID first
			clientUI.showUserId(is.readUTF());
			
			
			while (true) {
				// read from the is and parse the response to string, then send it to UI to display
				String serverResponse = is.readUTF();

				//if server sends out shut down request, exit
				if ("SERVER_SHUTDOWN".equals(serverResponse)) {
				    JOptionPane.showMessageDialog(null, "Server has disconnected you.");
				    System.exit(0);
				    break;
				}
				
				ServerResponseDTO serverResponseDTO = gson.fromJson(serverResponse, ServerResponseDTO.class);
				clientUI.showResult(serverResponseDTO); // once get the response from server, display it in the UI
			}
		
	    } catch (SocketException socketException) {
	        System.out.println("Server shuts down.");
	        JOptionPane.showMessageDialog(null, "The Server is closed");
	    } catch (IOException ioException) {
	    	ioException.printStackTrace();
		}finally {
			try {
				// close all resources
				is.close();
				os.close();
				clientSocket.close();
			    System.exit(0);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

	}

	/**
	 * send the dto to server(using Gson)
	 * 
	 * @param clientRequestDTO
	 */
	public void sendToServer(ClientRequestDTO clientRequestDTO) {
		try {
			String jsonStr = gson.toJson(clientRequestDTO);
			System.out.println("Client socket handler sent request to server:" + clientRequestDTO.toString());
			synchronized (os) {
				os.writeUTF(jsonStr);
			}
		} catch (IOException e) {
			System.out.println("IOException occurred: " + e.getMessage());
		}

	}

}
