package videolibrary.street.quality.qualityshow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;

public class SignUpActivity extends Activity implements View.OnClickListener, UserListener{

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.signUpButton).setOnClickListener(this);
        this.toolbar = (Toolbar) findViewById(R.id.signUp_toolBar);
        this.setActionBar(this.toolbar);

        setToolbar();
    }

    @Override
    public void onClick(View v) {
        EditText email = (EditText) findViewById(R.id.emailText);
        EditText username = (EditText) findViewById(R.id.nameText);
        EditText password = (EditText) findViewById(R.id.pwdText);

        Editable usernameEditable = username.getText();
        Editable mailEditable = email.getText();
        Editable pwdEditable = password.getText();

        boolean emptyMail = TextUtils.isEmpty(mailEditable);
        boolean emptyUsername = TextUtils.isEmpty(usernameEditable);
        boolean emptyPwd = TextUtils.isEmpty(pwdEditable);

        if(!emptyMail && !emptyPwd && !emptyUsername){
            QualityShowApplication.getUserHelper().create(usernameEditable.toString(), mailEditable.toString(), pwdEditable.toString(), "Default", this);
        } else {
            Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signup_menu_cancel) {
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

    }

    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    @Override
    public void userIsCreated(boolean user) {
        if(user){
            Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void userIsLogout() {

    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(SignUpActivity.this, "Erreur d'inscription, veuillez ré-essayer.", Toast.LENGTH_SHORT).show();
    }

    private void setToolbar(){
        setTitle("Inscription");
    }
}
