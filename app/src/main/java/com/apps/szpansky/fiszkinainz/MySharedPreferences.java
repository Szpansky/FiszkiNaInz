package com.apps.szpansky.fiszkinainz;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    static public int getLastQuestionId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("lastQuestionId",0);
    }

    static public void setLastQuestionId(Context context, int questionId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastQuestionId", questionId);
        editor.apply();
    }
}
