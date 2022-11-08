package com.dinsaren.mobilebankingserver.payload.response;

public class MessageRes {
    private String message;
    private String code;
    private Object data;

	public MessageRes() {
	}

	public MessageRes(String message, Object data) {
        this.message = message;
        this.data = data;
    }

	public MessageRes(Object data) {
		this.code = "200";
		this.message ="Success";
		this.data = data;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public void setMessageSuccess(Object data){
		this.code = "200";
		this.message ="Success";
		this.data = data;
	}

	public void badRequest(Object data){
		this.code = "400";
		this.message ="Bad Request";
		this.data = data;
	}

	public void internalServerError(Object data){
		this.code = "500";
		this.message ="Bad Request";
		this.data = data;
	}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "MessageRes{" +
				"message='" + message + '\'' +
				", code='" + code + '\'' +
				", data=" + data +
				'}';
	}
}
