package com.irynas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


    //To get a handle to a preference file, and to read, write, and manage preference data, use the SharedPreferences class.
    SharedPreferences mPreferences;
    private String sharedPrefFile = "com.irynas.myapplication";

    //keys to hold email, username and password in shared prefferences
    private final String EMAIL_KEY = "email";
    // Key for current color
    private final String PASSWORD_KEY = "password";


    /*

    * On the other hand, if Google Play services is an optional part of your app,
    * you can check the version only once the user navigates to that portion of your app.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Log.i(TAG, "started onCreate");

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        mEmailField = (EditText)findViewById(R.id.email);
        Log.i("Email field created", "created");

        mPasswordField = (EditText)findViewById(R.id.password);
        Log.i("Password field created", "created");

        // retrieve user email and password values from shared prefferences
        lastEmail = mPreferences.getString(EMAIL_KEY, "");
        lastPassword = mPreferences.getString(PASSWORD_KEY, "");

        // Update the value of the main TextView with the new count.
        mEmailField.setText(lastEmail);
        mPasswordField.setText(lastPassword);





        FirebaseApp.initializeApp(this);


        //In the onCreate() method, initialize the shared preferences.
        //opens the file at the given filename (sharedPrefFile) with the mode MODE_PRIVATE
        // mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Log.i("mPreferenc initialized", " successfully");



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

        //set last remembered email password in the field
        //Fetching last stored email and password from the database

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String userEmail = user.getEmail();
        //String userPassword = user.getPassword();

        //mEmailField.setText(userEmail);
        //mPasswordField.setText(lastPassword);

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

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        //adding the last used email and password to the shared prefferences
        preferencesEditor.putString(EMAIL_KEY, email);
        Log.d("EMAAIL", "ADDED TO SHARED PREFFERENCES");
        preferencesEditor.putString(PASSWORD_KEY, password);

        //Call apply() to save the preferences - saves the preferences asynchronously, off of the UI thread
        preferencesEditor.apply();

        //validate form here


        boolean isValid = validate(email, password);
        if (isValid) {
            //turning the entered info from email and passowd fields into variables
            lastPassword = mPasswordField.getText().toString();
            lastEmail = mEmailField.getText().toString();
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
        } else {
            //set the error
            mEmailField.setError("Required");
            mPasswordField.setError("Required");
        }
    }

    /**
     * Validates information in the email and password fields
     * @return
     */
    public boolean validate(String email, String password){
       // boolean result = true;

        if (email.length() == 0 || password.length() == 0) {

            //mEmailField.setError("Required");
            return  false;
        }


        return true;


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

    /**
     * You save the data in the onPause() lifecycle callback, and you need a shared editor object (SharedPreferences.Editor)
     * to write to the shared preferences object.
     */

    /*
    @Override
    protected void onPause(){
        super.onPause();
        // A shared preferences editor is required to write to the shared preferences object.
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        //adding the last used email and password to the shared prefferences
        preferencesEditor.putString(EMAIL_KEY, lastEmail);
        Log.d("EMAAIL", "ADDED TO SHARED PREFFERENCES");
        preferencesEditor.putString(PASSWORD_KEY, lastPassword);

        //Call apply() to save the preferences - saves the preferences asynchronously, off of the UI thread
        preferencesEditor.apply();
    }

    */
}
