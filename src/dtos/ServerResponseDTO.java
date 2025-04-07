package dtos;

import java.util.List;

public class ServerResponseDTO {
	private int code;
	private String msg;
	private List<String> contentList;
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public ServerResponseDTO(int code, String msg, List<String> contentList) {
		this.code=code;
		this.msg=msg;
		this.contentList=contentList;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<String> getContentList() {
		return contentList;
	}
	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}
	
	@Override
	public String toString() {
		return "ServerResponseDTO [code=" + code + ", msg=" + msg + ", contentList=" + contentList + ", toString()="
				+ super.toString() + "]";
	}
	
}
