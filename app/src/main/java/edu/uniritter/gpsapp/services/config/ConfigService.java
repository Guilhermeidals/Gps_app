package edu.uniritter.gpsapp.services.config;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigService {

    public static int GetInterval(Context context){
        SharedPreferences preferences = context.getSharedPreferences("INTERVAL", Context.MODE_PRIVATE);
        int interval = preferences.getInt("INVERVAL", 0);

        if (interval == 0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("INTERVAL", 3);
            editor.commit();
            interval = 3;
        }

        return interval;
    }

    public static void SetInterval(Context context, int interval){
        SharedPreferences preferences = context.getSharedPreferences("INTERVAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("INTERVAL", interval);
        editor.commit();
    }

    public static String GetDisplacement(Context context){
        SharedPreferences preferences = context.getSharedPreferences("DISPLACEMENT", Context.MODE_PRIVATE);
        String displacement = preferences.getString("DISPLACEMENT", null);

        if (displacement.equals(null)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("DISPLACEMENT", "caminhando");
            editor.commit();
            displacement = "caminhando";
        }

        return displacement;
    }

    public static void SetDisplacement(Context context, String displacement){
        SharedPreferences preferences = context.getSharedPreferences("DISPLACEMENT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("DISPLACEMENT", displacement);
        editor.commit();
    }

    public static boolean GetSaveStop(Context context){
        SharedPreferences preferences = context.getSharedPreferences("SAVESTOP", Context.MODE_PRIVATE);
        boolean saveStop = preferences.getBoolean("SAVESTOP", true);

        return saveStop;
    }

    public static void SetSaveStop(Context context, boolean saveStop){
        SharedPreferences preferences = context.getSharedPreferences("SAVESTOP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("SAVESTOP", saveStop);
        editor.commit();
    }
}

//        SharedPreferences preferences = getSharedPreferences("USER_LOGGED", MODE_PRIVATE);
//        String USER = preferences.getString("USER_LOGGED", null);

//        SharedPreferences preferences =  context.getSharedPreferences("USER_LOGGED", Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("USER_LOGGED", User);
//        editor.commit();