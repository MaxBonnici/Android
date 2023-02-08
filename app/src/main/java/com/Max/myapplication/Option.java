package com.Max.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Option extends AppCompatActivity {
    SharedPreferences keyFile;
    String deeplAuthKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        usage();
        keyFile = getSharedPreferences("keyFile", MODE_PRIVATE);

    }

    public void usage() {
        String deeplKey = "2966ae25-76f9-e86d-94a3-8feac167f6f7:fx";

        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key " + deeplKey)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Récuperation du nombre de character
                            String currentValue = String.valueOf(response.getInt("character_count"));
                            String limitValue = String.valueOf(response.getInt("character_limit"));

                            TextView counter = findViewById(R.id.counter);
                            counter.setText(currentValue + " / " + limitValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast.makeText(Option.this, "Erreur", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }
    public void keySetter(View view){
        EditText deeplKey = findViewById(R.id.authKey);
        deeplAuthKey = deeplKey.getText().toString();

        SharedPreferences.Editor editor = keyFile.edit();
        editor.putString("deeplKey", deeplAuthKey);
        editor.apply();

    }
}