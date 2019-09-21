package com.example.eautosalon.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eautosalon.data.UserVM;

public class MySession {

    private static final String PREFS_NAME = "SharedPreferenceEAutosalon";
    private static String key_user="Key_User";

    public static UserVM GetUser(){
        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String strJson = sharedPreferences.getString(key_user, "");
        if (strJson.length() == 0)
            return null;

        UserVM x = MyGson.build().fromJson(strJson, UserVM.class);
        return x;
    }

    public static void SetUser(UserVM user){
        String strJson = user!=null ? MyGson.build().toJson(user):"";

        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_user, strJson);
        editor.apply();
    }
}
