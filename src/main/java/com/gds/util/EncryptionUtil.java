package com.gds.util;

import java.util.Base64;


public class EncryptionUtil{
    
    public static String encode(String inputString) {
    	// encrypt data on your side using BASE64    	
    	String encodedString = 
    			  Base64.getEncoder().withoutPadding().encodeToString(inputString.getBytes());
    	return encodedString;
    }
    
    public static String decode(String encodedString) {
    	// encrypt data on your side using BASE64
    	byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    	return new String(decodedBytes);
    }
    
}
