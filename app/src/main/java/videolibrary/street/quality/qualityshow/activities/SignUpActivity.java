package videolibrary.street.quality.qualityshow.activities;


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

import com.strongloop.android.loopback.AccessToken;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;

public class SignUpActivity extends Activity implements View.OnClickListener, UserListener{

    ProgressActivity progressActivity;

    private View.OnClickListener errorClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        errorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        progressActivity = (ProgressActivity) findViewById(R.id.sign_up_progress);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        setTitle(getString(R.string.sign_up));
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
            progressActivity.showLoading();
            QualityShowApplication.getUserHelper().create(usernameEditable.toString(), mailEditable.toString(), pwdEditable.toString(), "Default", this);
        } else {
            Toast.makeText(this, "Please fill out all fields completely.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
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
            finish();
        }
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
    public void onError(Throwable e) {
        progressActivity.showError(getDrawable(R.drawable.ic_info_outline), "Erreur de connexion",
                "We could not establish a connection with our servers.",
                "Try Again", errorClickListener);
    }

}
