package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class Login {

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    static String getEncryptedPassword(String passwordText) throws NoSuchAlgorithmException {
        try{
            BigInteger number = new BigInteger(1, getSHA(passwordText));

            StringBuilder hexString = new StringBuilder(number.toString(16));
//        while (hexString.length())
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static boolean customerLogin(String email, String password)  {
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            String encryptedPassword = getEncryptedPassword(password);
            String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s' ", email,encryptedPassword);
            ResultSet rs = dbCon.getQueryTable(query);
            if(rs.next()){
                return  true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean customerreg(String email, String password,String first_name,String last_name,String addr,long mobile)  {
        try {
            String encrypted = getEncryptedPassword(password);
            DatabaseConnection dbCon = new DatabaseConnection();
            String query = String.format("INSERT INTO customer(`email`, `password`, `first_name`, `last_name`, `address`, `mobile`) VALUES ('%s', '%s', '%s', '%s', '%s', '%d')", email, encrypted, first_name, last_name, addr, mobile);
            if (dbCon.executeQuery(query)) {
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
public static boolean isexists(String user)
{
    try{
        DatabaseConnection dbCon = new DatabaseConnection();
        String query = String.format("SELECT * FROM customer WHERE email = '%s'  ", user);
        ResultSet rs = dbCon.getQueryTable(query);
        if(rs.next()){
            return  true;
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    return false;

}
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String pass = "angad123";
//        System.out.println(pass);
//        System.out.println(Login.getEncriptedPassword(pass));
        System.out.println(Login.customerreg("iliyas", "abc","iliyas","null","ads123456",1234567890));
       // System.out.println(Login.customerLogin("accio@gmail.com", "an6t5gad123"));
    }


}
