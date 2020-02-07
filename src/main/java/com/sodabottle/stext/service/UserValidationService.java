package com.sodabottle.stext.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.sodabottle.stext.models.User;

@Service
public class UserValidationService {
	
	public static boolean isValidEmail(String email) { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (null == email) 
            return false; 
        return pat.matcher(email).matches(); 
    }
	
	public static boolean isValidMobile(String mobile) 
    { 
        Pattern p = Pattern.compile("(0/+91/91)?[0-9]{10,13}");
  
        Matcher m = p.matcher(mobile); 
        return (m.find() && m.group().equals(mobile)); 
    }

	public boolean validateUser(User user) {
		
		System.out.println(isValidEmail(user.getEmail()));

		if(isValidEmail(user.getEmail()) && isValidMobile(user.getMobile()))
			return true;
		return false;
	}
}
