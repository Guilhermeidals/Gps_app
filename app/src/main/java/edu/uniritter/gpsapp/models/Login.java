package edu.uniritter.gpsapp.models;

import android.content.Context;
import android.content.SharedPreferences;

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

    public String getUserLogged(){ return USER_LOGGED; }

    public Login(){
    }

    public boolean Validate(Context context){

        if (User.equals("") || Password.equals(""))
            return false;
        if(!USERS.containsKey(User))
            return false;
        if(!(Password.equals(USERS.get(User))))
            return false;

        SaveUser(context, User);

        return true;
    }

    public  void SaveUser(Context context, String user){
        SharedPreferences preferences =  context.getSharedPreferences("USER_LOGGED", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USER_LOGGED", User);
        editor.commit();
    }
}

