package com.irynas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import static android.content.Intent.EXTRA_TEXT;

public class MainActivity extends AppCompatActivity {

    private static boolean WIFIconnected = false;
    private static boolean mobileConnected = false;

    private EditText mEmailField;
    private EditText mPasswordField;

    private String lastEmail;
    private String lastPassword;

    private static final String TAG = "Main Activity";
    private static final String NAME = "AD340 - IRYNA";
    private ProgressBar progressBar;




    // rewrite to Kotlin later
    //Creating the lists of movie names
    //String[] NAMES = {"Night of the Comet", "Dead Snow","Cemetery Man", "28 Weeks Later","Night of the Creeps", "ParaNorman","Zombieland", "Planet Terror", "Train to Busan"};
    //method to your main activity that starts a new activity when the upper-left button is clicked,

    // if Google Play services is required for your app at all times, you might want to do it when your app first launches
/*

On the other hand, if Google Play services is an optional part of your app,
you can check the version only once the user navigates to that portion of your app.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Log.i(TAG, "started onCreate");

        FirebaseApp.initializeApp(this);



       Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ListViewItemsActivity.class);
                    startActivity(intent);
                }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Button mapButton = (Button)findViewById(R.id.button3);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

            }
        });


        //-------------LOGIN AND AUTHENTIFICATION

        Button loginButton = (Button)findViewById(R.id.login);
        //When the Login button is clicked;

        /**
         * When the Login button is clicked;
         * Validate the each text-field is non-empty
         * Prevent navigation & show a warning if any fields are invalid,
         * Store valid user-entered text to shared preferences
         * if all entries are valid, navigate to a new activity called Team.
         * You can use this activityPreview the document & layoutPreview the document as starting points
         */
        mEmailField = (EditText)findViewById(R.id.email);

        mPasswordField = (EditText)findViewById(R.id.password);
        //set last remembered email password in the field
        //Fetching last stored email and password from the database

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        //String userPassword = user.getPassword();

        mEmailField.setText(userEmail);
        mPasswordField.setText(lastPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signIn();
                //Attempt to invoke virtual method 'android.text.Editable android.widget.EditText.getText()' on a null object reference

               // Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
                //startActivity(intent);
            }
        });


    }

    /**
     * Sign in code to call on Login button click
     */
    private void signIn() {

        Log.d(TAG, "signIn");


        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        //validate form here


        boolean isValid = validate(email, password);
        if (isValid) {
            //turning the entered info from email and passowd fields into variables

            // sign into Firebase project
            //FirebaseApp.initializeApp(getApplicationContext());
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("FIREBASE", "signIn:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()) {
                                // update profile
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(NAME)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("FIREBASE", "User profile updated.");
                                                    // Go to FirebaseActivity
                                                    startActivity(new Intent(MainActivity.this, TeamActivity.class));
                                                }
                                            }
                                        });

                            } else {
                                Log.d("FIREBASE", "sign-in failed");

                                Toast.makeText(MainActivity.this, "Sign In Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
    }

    /**
     * Validates information in the email and password fields
     * @return
     */
    public boolean validate(String email, String password){
        boolean result = true;
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required");
            result = false;

        } else {
            mEmailField.setError(null);
            lastEmail = mEmailField.getText().toString();
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required");
            result = false;

        } else {
            mPasswordField.setError(null);
            lastPassword = mPasswordField.getText().toString();
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
            //return true;
       // }
        switch(item.getItemId()){
            case R.id.list_settings:
                Intent list = new Intent(this, ListViewItemsActivity.class);
                startActivity(list);
                break;
            case R.id.about_settings:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.info_icon:
                Intent aboutIcon = new Intent(this, AboutActivity.class);
                startActivity(aboutIcon);
                break;
            case R.id.location_icon:

                    Intent locationCameras = new Intent(this, CameraActivity.class);
                    startActivity(locationCameras);

                break;

            default:

        }

        return super.onOptionsItemSelected(item);
    }


}
