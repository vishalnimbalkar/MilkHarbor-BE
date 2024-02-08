package com.milkharbor.encrypt;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {
	 	private static final String AES_ALGORITHM = "AES";
	    private static final String ENCRYPTION_KEY = "MH@milkharbor1234";
	    
	    public String encrypt(String password) throws Exception {
	        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), AES_ALGORITHM);

	        // Generate a random IV
	        byte[] ivBytes = new byte[16];
	        new SecureRandom().nextBytes(ivBytes);
	        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

	        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
	        
	        // Combine IV and encrypted data
	        byte[] combinedData = new byte[16 + encryptedBytes.length];
	        System.arraycopy(ivBytes, 0, combinedData, 0, 16);
	        System.arraycopy(encryptedBytes, 0, combinedData, 16, encryptedBytes.length);

	        // Encode the combined data as Base64
	        String encryptedPassword = Base64.getEncoder().encodeToString(combinedData);

	        return encryptedPassword;
	    }
	    
	    public String decrypt(String encryptedPassword) throws Exception {
	        SecretKeySpec secretKey = new SecretKeySpec("MH@milkharbor1234".getBytes(), "AES");

	        // Decode the Base64-encoded combined data (IV + encrypted data)
	        byte[] combinedData = Base64.getDecoder().decode(encryptedPassword);

	        // Extract the IV from the combined data
	        byte[] ivBytes = new byte[16];
	        System.arraycopy(combinedData, 0, ivBytes, 0, 16);
	        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

	        // Extract the encrypted data from the combined data
	        byte[] encryptedBytes = new byte[combinedData.length - 16];
	        System.arraycopy(combinedData, 16, encryptedBytes, 0, combinedData.length - 16);

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

	        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
	        return new String(decryptedBytes);
	    }
}
