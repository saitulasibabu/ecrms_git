/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vwoa.ecrms.security.LoginSecurity;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to read values from properties
 * 
 * @author Aparna_Deshmukh01
 * 
 */
public class PropertyReader {
	private static Log log = LogFactory.getLog(PropertyReader.class);
	private static Properties propsFromFile = null;
	private static String PROPS_FILE_NAME = "ecrms_config";

	/**
	 * This method gets the values of the keys from the properties file
	 * 
	 * @param key
	 *            the key of which values is to be fetched
	 * @return value of the key
	 * 
	 */
	public static String retrievePropertiesValue(String key)
	throws ECRMSException {
		if (propsFromFile == null) {
			propsFromFile = loadProperties();
		}
		return propsFromFile.getProperty(key);
	}

	/**
	 * This method load the  properties file
	 * 
	 * @return properties file
	 */
	private static Properties loadProperties() {
		propsFromFile = new Properties();

		try {
			String env = LoginSecurity.retrieveEnvironment();
			if(env == null || env.equals("")) PROPS_FILE_NAME = PROPS_FILE_NAME+".properties";
			else PROPS_FILE_NAME = PROPS_FILE_NAME +"_" + env +".properties";

			propsFromFile.load(PropertyReader.class.getClassLoader()
					.getResourceAsStream(PROPS_FILE_NAME));

			log.debug("loaded properties file");

		} catch (IOException ioe) {

			log.error("Error occured while loading properties file");

		} catch (Exception ex) {
			log.error("Error occured while loading properties file");
		}
		return propsFromFile;
	}

}
