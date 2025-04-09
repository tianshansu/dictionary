package dtos;

import java.util.List;

/**
 * Server Response Data Transmission Object
 */
public class ServerResponseDTO {
	private int code;
	private String msg;
	private List<String> contentList;
	
	/**
	 * get message
	 * @return message from server
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * constructor
	 * @param code status code
	 * @param msg message
	 * @param contentList content(meaning list)
	 */
	public ServerResponseDTO(int code, String msg, List<String> contentList) {
		this.code=code;
		this.msg=msg;
		this.contentList=contentList;
	}
	
	/**
	 * get status code
	 * @return code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * get content list (meaning list)
	 * @return
	 */
	public List<String> getContentList() {
		return contentList;
	}
	
	/**
	 * to string
	 */
	@Override
	public String toString() {
		return "ServerResponseDTO [code=" + code + ", msg=" + msg + ", contentList=" + contentList + ", toString()="
				+ super.toString() + "]";
	}
	
}
