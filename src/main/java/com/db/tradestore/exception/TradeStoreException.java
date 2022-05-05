package com.db.tradestore.exception;

public class TradeStoreException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private final String errorCode;
	
	public TradeStoreException(String message,String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
