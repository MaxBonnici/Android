package com.Max.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ImageButton myHistory;
    ImageButton myOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);
        loadLanguages();


        myHistory = (ImageButton) findViewById(R.id.history);
        myOption = (ImageButton) findViewById(R.id.option);

        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
            }
        });
        myOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Option.class);
                startActivity(intent);
            }
        });
    }

    public class Language {
        private String language;
        private String name;

        public Language(String language, String name) {
            this.language = language;
            this.name = name;
        }

        public String getLanguage() {
            return language;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }
    }
    public void loadLanguages() {
            String deeplKey = "2966ae25-76f9-e86d-94a3-8feac167f6f7:fx";

            Context that = this;
            AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                    .addHeaders("Authorization", "DeepL-Auth-Key " + deeplKey)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                // Liste dans laquelle seront stockés les langues
                                ArrayList<Language> languageList = new ArrayList<>();
                                for (int iLanguage = 0; iLanguage < response.length(); ++iLanguage) {
                                    final JSONObject language = response.getJSONObject(iLanguage);
                                    languageList.add(new Language(
                                            language.getString("language"),
                                            language.getString("name")
                                    ));
                                    System.out.println(language.getString("language"));
                                }
                                // Création d’un adaptateur permettant d’afficher les langues dans un Spinner
                                ArrayAdapter<Language> adapter = new ArrayAdapter<>(
                                        that,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        languageList
                                );
                                // Réupération du spinner
                                Spinner spinnerFinale = findViewById(R.id.langueFinale);
                                // Mise en place de l’adaptateur dans le spinner
                                spinnerFinale.setAdapter(adapter);
                                Toast toast = Toast.makeText(MainActivity.this, "Work", Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast toast = Toast.makeText(MainActivity.this, "Erreur31312321412", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            Toast toast = Toast.makeText(MainActivity.this, "Erreur", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
        }
    }