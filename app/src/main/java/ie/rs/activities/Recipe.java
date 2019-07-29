package ie.rs.activities;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ie.rs.R;
import ie.rs.db.DatabaseHelper;

public class Recipe extends AppCompatActivity {

    DatabaseHelper myDB;
    Button btnAdd,btnView;
    EditText editText;
    private ImageView chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        myDB = new DatabaseHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView chef = (ImageView) findViewById(R.id.imageView2);

        chef.setImageResource(R.drawable.recipe);





        //When the add button is selected it takes the string from the editText box
        //  and adds the data to the database but only if it is not empty, its displayed in the list view
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length()!= 0){
                    AddData(newEntry);
                    editText.setText("");
                }else{
                    //If the editText box is empty a toast will appear
                    Toast.makeText(Recipe.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Allows the user to switch to the other activity
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recipe.this, RecipeList.class);
                startActivity(intent);

            }
        });


    }





    //method to add data to the database
    public void AddData(String newEntry) {

        boolean insertData;
        insertData = myDB.addData(newEntry);

        if(insertData){
            Toast.makeText(this, "Added to list!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                //case R.id.menuHome:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Register.class));
                Toast.makeText(Recipe.this, "Logged Out!" , Toast.LENGTH_SHORT).show();


                // startActivity(new Intent(this, Home.class));

                break;


        }

        return true;
    }
}


