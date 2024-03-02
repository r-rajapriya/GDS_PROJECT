package com.gds.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EncryptionUtil class for password hashing function
 * 
 * @author Rajapriya
 *
 */
public class EncryptionUtil{
	
	private static final Logger log = LogManager.getLogger(EncryptionUtil.class);
	
	/**
	 * Method to perform MD5 hashing for the user input password
	 * 
	 * @param pwd
	 * @return
	 */
	public static String hashPassword(String pwd)
	{
		log.error("hashPassword method is called");
		String hashedPwd = ""; 
		try {
			MessageDigest md = MessageDigest.getInstance(AppConstants.PWD_HASH_METHOD);
			md.update(pwd.getBytes());
		    byte[] digest = md.digest();
		    hashedPwd = DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			log.error("Error while hashing the password - "+e.getMessage());
		}	    
	    return hashedPwd;
	}  
}
