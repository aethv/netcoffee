package com.clinet.utils;

import java.util.Random;
import java.util.UUID;

public class CommonUtils {

	public static String getRandomPassword() {
		  StringBuffer buffer = new StringBuffer();
		  Random random = new Random();
		  char[] alphabetChars = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
		  char[] specialChars = new char[] {'!','@','#','$','%','^','&'};
		  char[] numberChars = new char[] {'0','1','2','3','4','5','6','7','8','9'};
		  for (int i = 0; i < 5; i++) {
		    buffer.append(alphabetChars[random.nextInt(alphabetChars.length)]);
		  }
		  for(int i =0; i<3; i++){
			  buffer.append(specialChars[random.nextInt(specialChars.length)]);  
		  }
		  for(int i =0; i<3; i++){
			  buffer.append(numberChars[random.nextInt(numberChars.length)]);  
		  }		  
		  return buffer.toString();
	}
	
	public static String getRandom8Chars(){
        String result = "";
        try{
            UUID uID = UUID.randomUUID();
            result = uID.toString().substring(0,8);//first 8 chars
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
