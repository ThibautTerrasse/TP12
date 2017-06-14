package fr.formation.tp12;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Secondaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondaire);

        Button boutonSave = (Button) findViewById(R.id.save);
        final EditText nomSaisi =(EditText) findViewById(R.id.taperNom);

        boutonSave.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick (View v){

                Intent intent = new Intent(Secondaire.this, Principale.class);
                if(!nomSaisi.getText().equals("")){
                    intent.putExtra("NomUser", nomSaisi.getText().toString());

                }
                startActivity(intent);
            }
        });
    }
}
