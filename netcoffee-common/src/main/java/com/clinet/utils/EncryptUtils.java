package com.clinet.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptUtils {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getMD5Digest("client1abc"));
		System.out.println(getMD5Digest("client2abc"));
	}
	
	public static String getRandomPassword() {
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		char[] alphabetChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
				'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		char[] specialChars = new char[] { '!', '@', '#', '$', '%', '^', '&' };
		char[] numberChars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		for (int i = 0; i < 5; i++) {
			buffer.append(alphabetChars[random.nextInt(alphabetChars.length)]);
		}
		for (int i = 0; i < 3; i++) {
			buffer.append(specialChars[random.nextInt(specialChars.length)]);
		}
		for (int i = 0; i < 3; i++) {
			buffer.append(numberChars[random.nextInt(numberChars.length)]);
		}
		return buffer.toString();
	}
	
	public static String getMD5Digest(String str) throws Exception {
        try {
        	MessageDigest md5 = MessageDigest.getInstance("MD5");
        	md5.update(str.getBytes());
            
        	byte[] byteData = md5.digest();
            
        	//convert the byte to hex format method 1
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
            	buf.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
    }
}
