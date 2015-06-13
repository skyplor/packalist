package com.skypayjm.thack.packalist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hugo.weaving.DebugLog;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    Firebase firebaseRef;

    @AfterViews
    protected void init() {
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://packalist.firebaseio.com");
    }

    @ViewById
    EditText userEmail, userPassword;

    @ViewById
    View snackbarPosition;

    @Click
    public void create_login() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        if (emailValid(email) && passwordValid(password)) {
            login(email, password);
        }
    }

    private boolean emailValid(String text) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private boolean passwordValid(String text) {
        if (text.length() == 0) return false;
        return true;
    }

    @DebugLog
    private void login(final String userEmail, final String userPassword) {
        firebaseRef.authWithPassword(userEmail, userPassword, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Snackbar.make(snackbarPosition, "Login successful", Snackbar.LENGTH_LONG).show();
                goToNextActivity();
            }

            @DebugLog
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                if (firebaseError.getCode() == FirebaseError.USER_DOES_NOT_EXIST) {
                    createUser(userEmail, userPassword);
                }
            }
        });
    }

    private void goToNextActivity() {
        Intent searchIntent = new Intent(MainActivity.this, SearchActivity_.class);
        startActivity(searchIntent);
    }

    @DebugLog
    private void createUser(final String userEmail, final String userPassword) {
        firebaseRef.createUser(userEmail, userPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                login(userEmail, userPassword);
            }

            @DebugLog
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
