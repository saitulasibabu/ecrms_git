/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.util.exception;

/**
 * 
 * This is a custom Exception class for ECRMS exceptions. Any exception
 * originated at any lower level has to be propagated to the parent level in the
 * form of this Exception class.
 * <p>
 * In context of the ECRMS application, this Exception class would act as a
 * wrapper for all the ECRMS or Java specific exceptions that originate at lower
 * layers.
 * 
 * @see Throwable
 * @see RuntimeException
 * @author aparna_deshmukh01
 * 
 */
public class ECRMSException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5925606075334423998L;

	private int errorCode = 0;

	private String description = null;

	/**
	 * This is a no-arg constructor for the ECRMSException class.
	 * <p>
	 * 
	 * @param
	 * @return None constructor returns an object of the same class
	 */
	public ECRMSException() {
		/*
		 * Any relevant initializations for the ECRMSException object should be
		 * added here
		 */
	}

	/**
	 * This is a parameterized constructor for the ECRMSException class. It
	 * expects a String as argument.
	 * <p>
	 * The passed String is a message which would be assigned to the description
	 * {@link String} field of this class.
	 * 
	 * @param errorCode
	 *            Detailed message for this Exception
	 * @return None constructor returns an object of the same class
	 */
	public ECRMSException(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * This is a parameterized constructor for the ECRMSException class. It
	 * expects a String as argument.
	 * <p>
	 * The passed String is a message which would be assigned to the description
	 * {@link String} field of this class.
	 * 
	 * @param message
	 *            Detailed message for this Exception
	 * @return None constructor returns an object of the same class
	 */
	public ECRMSException(int errorCode, String message) {

		/**
		 * Any relevant initializations for the ECRMSException object should be
		 * added here
		 */
		super(message);
		this.errorCode = errorCode;
		this.description = message;
	}

	/**
	 * This is a parameterized constructor for the ECRMSException class. It
	 * expects a Throwable as argument.
	 * <p>
	 * The passed Throwable argument is an object which would be assigned to the
	 * cause {@link Throwable} field of this class.
	 * 
	 * @param cause
	 *            Throwable instance representing the actual causing Exception
	 * @return None constructor returns an object of the same class
	 */
	public ECRMSException(Throwable cause) {
		/*
		 * Any relevant initializations for the ECRMSException object should be
		 * added here
		 */
		super(cause.getMessage());
	}

	/**
	 * This is a parameterized constructor for the ECRMSException class. It
	 * expects a String and a Throwable object as arguments.
	 * <p>
	 * The passed String is a message which would be assigned to the errorCode
	 * {@link String} field and Throwable object would be assigned to the cause
	 * {@link Throwable} field of the same class.
	 * 
	 * @param message
	 *            Detailed message for this Exception
	 * @param cause
	 *            Throwable instance representing the actual causing Exception
	 * @return None constructor returns an object of the same class
	 */
	public ECRMSException(String message, Throwable cause) {
		/*
		 * Any relevant initializations for the ECRMSException object should be
		 * added here
		 */
		super(cause.getMessage(), cause);
		this.description = message;
	}

	/**
	 * This is a parameterized constructor for the ECRMSException class. It
	 * expects a String and a Throwable object as arguments.
	 * <p>
	 * The passed String is a message which would be assigned to the errorCode
	 * {@link String} field and Throwable object would be assigned to the cause
	 * {@link Throwable} field of the same class.
	 * 
	 * @param message
	 *            Detailed message for this Exception
	 * @param cause
	 *            Throwable instance representing the actual causing Exception
	 * @return None constructor returns an object of the same class
	 */
	public ECRMSException(int errorCode, String message, Throwable cause) {
		super(cause.getMessage(), cause);
		/*
		 * Any relevant initializations for the ECRMSException object should be
		 * added here
		 */
		this.errorCode = errorCode;
		this.description = message;
	}

	/**
	 * @return description
	 * */
	public String getDescription() {
		return description;
	}

	/**
	 * @return errorCode
	 * */
	public int getErrorCode() {
		return errorCode;
	}

}
