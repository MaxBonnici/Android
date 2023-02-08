package com.Max.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class History extends AppCompatActivity {
    SharedPreferences historyfile;
    ArrayList<Traduction> tradList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyfile = getSharedPreferences("historyfile", MODE_PRIVATE);


        Gson gson = new Gson();
        String jsonText = historyfile.getString("Historique", new ArrayList<Traduction>().toString());
        Type type = new TypeToken<ArrayList<Traduction>>(){}.getType();
        tradList = gson.fromJson(jsonText,type);
        System.out.println(tradList.get(0).getTranslatedText());
    }

}