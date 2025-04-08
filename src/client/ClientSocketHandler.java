package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dtos.ClientRequestDTO;
import dtos.ServerResponseDTO;

/**
 * Client communicate with the server, client send and receive data from the
 * server
 */
public class ClientSocketHandler implements Runnable {
	Socket clientSocket;
	ClientUI clientUI;
	DataInputStream is;
	DataOutputStream os;
	Gson gson = new Gson();

	public ClientSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setClientUI(ClientUI clientUI) {
		this.clientUI = clientUI;
	}

//	public Socket getClientSocket() {
//		return this.clientSocket;
//	}

	@Override
	public void run() {
		System.out.println(" this client socket handler is running ... ");
		// keep reading the information send by the server
		try {
			while (true) {
				// read from the is and parse the response to string, then send it to UI to display
				String serverResponse = is.readUTF();
				ServerResponseDTO serverResponseDTO = gson.fromJson(serverResponse, ServerResponseDTO.class);
				clientUI.showResult(serverResponseDTO); // once get the response from server, display it in the UI
			}
		}catch (EOFException eofException) {
	        System.out.println("Server closed the connection.");
	    } catch (SocketException socketException) {
	        System.out.println("Connection reset by server.");
	    } catch (IOException ioException) {
	    	ioException.printStackTrace();
		}finally {
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

	/**
	 * send the dto to server(using Gson)
	 * 
	 * @param clientRequestDTO
	 */
	public void sendToServer(ClientRequestDTO clientRequestDTO) {
		try {
			String jsonStr = gson.toJson(clientRequestDTO);
			System.out.println("Client socket handler sent request to server:" + clientRequestDTO.toString());
			os.writeUTF(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
