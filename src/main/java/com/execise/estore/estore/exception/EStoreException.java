package com.execise.estore.estore.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EStoreException extends RuntimeException {

	private String message;

	public EStoreException(String message) {
		super();
		this.message = message;
	}
	
//	private final String messageKey;
//	private final Locale locale;
//	private String messageInfo;
//	
//	public EStoreException(String messageKey) {
//		this(messageKey,"", Locale.getDefault()) ;
//	}
//	
//	public EStoreException(String messageKey, String messageInfo) {
//		this(messageKey,messageInfo, Locale.getDefault()) ;
//	}
//
//	public EStoreException(String messageKey, String messageInfo, Locale locale) {
//		super();
//		this.messageKey = messageKey;
//		this.locale = locale;
//		this.messageInfo=messageInfo;
//		
//		//DEBUG:
//		log.info("locale from EStoreException constructor: " + locale);
//	}
//	
//	public String getLocalizedMessage() {
//		return Messages.getMessageForLocale(messageKey, locale)+messageInfo;
//	}
//	//to avoid considering which logging library we use to getLocalizedMessage 
//	//instead of getting the default getMessage
//	//for this end, we override getMessage().
//	public String getMessage() {
//		return this.getLocalizedMessage();
//	}
}
