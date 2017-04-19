package com.voting.security;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class WebTokenEncryption {
	
	private static final String UNICODE_FORMAT ="UTF8";
	private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	byte[] arrayBytes;
	private String encryptionKey;
	private String encryptionScheme;
	private SecretKey key;
	
	public WebTokenEncryption() throws Exception {
		
		encryptionKey = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUV=="; //really long
		encryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = encryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(encryptionScheme);
		cipher = Cipher.getInstance(encryptionScheme);
		key = skf.generateSecret(ks);
		
	}
	
	public String encrypt(String unencrypted) {
		
		String encryptedString = null;
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencrypted.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encryptedString;
	
	}
	
	public String decrypt(String encrypted) {
		
		String decrypted = null;
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encrypted);
			byte[] plainText = cipher.doFinal(encryptedText);
			decrypted = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return decrypted;
		
	}
	
}