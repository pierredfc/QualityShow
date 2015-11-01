package videolibrary.street.quality.qualityshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;


public class LoginActivity extends Activity implements UserListener, View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.login_menu_cancel) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {
        Toast.makeText(getApplicationContext(), "Bienvenue " + user.getUsername(), Toast.LENGTH_LONG).show();

     /*   SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        prefs.edit().putString("Email", user.getEmail()).apply();
        prefs.edit().putString("Password", user.getPassword()).apply();*/

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    @Override
    public void userIsCreated(boolean user) {

    }

    @Override
    public void userIsLogout() {

    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void userIsRetrieved(User user) {

    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(getApplicationContext(), "Erreur lors de la connexion", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        EditText email = (EditText) findViewById(R.id.login_email_text);
        EditText password = (EditText) findViewById(R.id.login_pwd_txt);

        Editable mailEditable = email.getText();
        Editable pwdEditable = password.getText();

        boolean emptyMail = TextUtils.isEmpty(mailEditable);
        boolean emptyPwd = TextUtils.isEmpty(pwdEditable);

        if (!emptyMail && !emptyPwd) {
            QualityShowApplication.getUserHelper().login(mailEditable.toString(), pwdEditable.toString(), this);
        } else {
            Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show();
        }
    }
}
