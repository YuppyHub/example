package com.tianbao54.spring.example.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class QuickPasswordEncodingGenerator {

	 public static void main(String[] args) {
         String password = "yupeng54";
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         System.out.println(passwordEncoder.encode(password));
         boolean result = passwordEncoder.matches("yupeng54", "$2a$10$./.50cDtWT5BeKYedMpsNenkf9N5g/X4A91ymy4wIlmkL.7jg.YlK");
         System.out.println(result);
 }
}
