package fr.formation.tp12;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import fr.formation.tp12.database.modele.User;

public class Secondaire extends AppCompatActivity {

    private EditText nomSaisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondaire);

        nomSaisi= (EditText) findViewById(R.id.taperNom);
    }

    public void sauvegarder(View v) {

        User utilisateur = new User();
        utilisateur.setNom(nomSaisi.getText().toString());
        // Transformation en JSON :
        String flux = (new Gson().toJson(utilisateur));
        Log.d("Utilisateur en JSON", flux);

        // On dépose notre utilisateur jsonné dans l'intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("NomUser", flux);
        setResult(2, resultIntent);

        // Bye l'activité
        finish();

    }
}
