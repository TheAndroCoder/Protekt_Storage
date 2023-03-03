package com.theandrocoder.protektstorage.utils;

public class Utils {
    public static boolean validateEmail(String email){
        // TODO : complete logic using regex
        return true;
    }

    public static boolean validatePassword(String password){
        // TODO : complete logic using regex
        return true;
    }

    public static boolean validateUsername(String username){
        return username.length()>=3;
    }

    public static boolean validateEmailPassUser(String email, String password, String username) {
        return validateEmail(email) && validatePassword(password) && validateUsername(username);
    }

    public static boolean validateEmailAndPass(String email,String password){
        return validateEmail(email) && validatePassword(password);
    }
}
