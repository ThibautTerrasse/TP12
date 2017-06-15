package fr.formation.tp12;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fr.formation.tp12.database.datasource.DataSource;
import fr.formation.tp12.database.modele.User;

public class Principale extends AppCompatActivity {

    DataSource<User> dataSource;
    private RecyclerView mRecyclerView;
    FloatingActionButton boutonAjouter;
    private RecyclerViewAdapter adapter;
    private List<User> utilisateurs = new ArrayList<>();
    private long lastBackPressTime = 0;
    private int versionDB = 1; // Permet de detruire la base de données SQLite si on incrémente la version


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);

         boutonAjouter = (FloatingActionButton) findViewById(R.id.ajouterUser);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);



        adapter = new RecyclerViewAdapter(utilisateurs, android.R.layout.simple_list_item_1);
        mRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String nomUser= intent.getStringExtra("NomUser");

        boutonAjouter.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick (View v){

                Intent intent = new Intent(Principale.this, Secondaire.class);
                startActivityForResult(intent, 2);
            }
        });
        chargerUtilisateurs();

       /* // Create or retrieve the database
        try {
            dataSource = new DataSource<>(this, User.class, versionDB);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // open the database
        /*openDB();

        // Insert a new record
        // -------------------
        User user = new User();
        user.setNom(nomUser);
        try {
            insertRecord(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // update that line
        // ----------------
      /* try {
            user.setNom("Bidochon");
            updateRecord(user);
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Query that line
        // ---------------
        queryTheDatabase();

        // And then delete it:
        // -------------------
        //deleteRecord(user);


*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(this, User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        chargerUtilisateurs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //closeDB();
    }

   public void openDB() throws SQLiteException {
        dataSource.open();
    }

    public void closeDB() {
        dataSource.close();
    }

    private long insertRecord(User user) throws Exception {

        // Insert the line in the database
        long rowId = dataSource.insert(user);

        // Test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Error when creating an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User created and stored in database",
                    Toast.LENGTH_LONG).show();
        }
        return rowId;
    }

    /**
     * * Update a record
     *
     * @return the updated row id
     */
    private long updateRecord(User user) throws Exception {

        int rowId = dataSource.update(user);

        // test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Error when updating an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User updated in database", Toast.LENGTH_LONG)
                    .show();
        }
        return rowId;
    }

    private void deleteRecord(User user) {
        long rowId = dataSource.delete(user);
        if (rowId == -1) {
            Toast.makeText(this, "Error when deleting an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User deleted in database", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Query a the database
     */
    private void queryTheDatabase() {
        List<User> users = dataSource.readAll();
        displayResults(users);
    }

    private void displayResults(List<User> users) {

        int count = 0;
        for (User user : users
                ) {
            Toast.makeText(
                    this,
                    "Utilisateur :" + user.getNom() + "("
                            + user.getId() + ")", Toast.LENGTH_LONG).show();
            count++;
        }
        Toast.makeText(this,
                "The number of elements retrieved is " + count,
                Toast.LENGTH_LONG).show();

    }
    private void chargerUtilisateurs() {
        // On charge les données depuis la base.
        try {
            List<User> users = dataSource.readAll();
            utilisateurs.clear();
            utilisateurs.addAll(users);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            // Que faire ?
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 2) {

            String flux = data.getStringExtra("NomUser"); // Tester si pas null ;-)
            User utilisateur = new Gson().fromJson(flux, User.class);

            try {
                dataSource.insert(utilisateur);
            } catch (Exception e) {
                // Que faire :-(
                e.printStackTrace();
            }

            // Indiquer un changement au RecycleView
            chargerUtilisateurs();


        }

    }
}
