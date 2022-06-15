package edu.uniritter.gpsapp.models;

import java.util.HashMap;
import java.util.Map;

public class Login {
    private static Map<String, String> USERS = new HashMap<String, String>();
    static {
        USERS.put("admin", "admin123");
        USERS.put("operator", "operator123");
    }

    private String USER_LOGGED;

    private String User;
    private String Password;

    public String getUser(){
        return User;
    }

    public void setUser (String user){
        User = user;
    }

    public String getPassword(){
        return Password;
    }

    public void setPassword(String password){
        Password = password;
    }

    public Login(){
        USERS.put("admin", "admin123");
        USERS.put("operator", "operator123");
    }

    public boolean Validate(){

        if (User.equals("") || Password.equals(""))
            return false;
        if(!USERS.containsKey(User))
            return false;
        if(!(Password.equals(USERS.get(User))))
            return false;

        USER_LOGGED = User;
        return true;
    }
}

