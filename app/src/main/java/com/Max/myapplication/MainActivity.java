package com.Max.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                                }
                                // Création d’un adaptateur permettant d’afficher les langues dans un Spinner
                                ArrayAdapter<Language> adapter = new ArrayAdapter<>(
                                        that,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        languageList
                                );

                                // Réupération du spinner
                                Spinner spinnerFinale = findViewById(R.id.spin_language);

                                // Mise en place de l’adaptateur dans le spinner
                                spinnerFinale.setAdapter(adapter);
                                } catch (JSONException e) {
                                e.printStackTrace();
                                }
                        }
                        @Override
                        public void onError(ANError anError) {
                            Toast toast = Toast.makeText(MainActivity.this, "Erreur", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
        }
    public void translate(View view) {
            String deeplKey = "2966ae25-76f9-e86d-94a3-8feac167f6f7:fx";

            Context that = this;
            final Spinner language = findViewById(R.id.spin_language);
            Language selectedLanguage = (Language) language.getSelectedItem();

            final EditText text = findViewById(R.id.textSource);
            String textToTranslate = text.getText().toString();

            AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                    .addHeaders("Authorization", "DeepL-Auth-Key " + deeplKey)
                    .addBodyParameter( "text", textToTranslate)
                    .addBodyParameter( "target_lang", selectedLanguage.getLanguage())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //Récuperation du tableau des langues
                                JSONArray translations = response.getJSONArray("translations");
                                //Récuperation de la traduction
                                final JSONObject translation = translations.getJSONObject(0);
                                //Récuperation du tableau du texte traduit
                                String translatedText = translation.getString("text");
                                //Récuperation langue source
                                String detectedSource = translation.getString("detected_source_language");
                                Toast toast = Toast.makeText(MainActivity.this, "Langue détectée: " + detectedSource, Toast.LENGTH_LONG);
                                toast.show();
                                
                                TextView textView = findViewById(R.id.textFinal);
                                textView.setText(translatedText);
                            } catch (JSONException e) {
                                e.printStackTrace();
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