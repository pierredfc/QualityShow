package videolibrary.street.quality.qualityshow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;

public class SignUpActivity extends Activity implements View.OnClickListener, UserListener{

    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.signUpButton).setOnClickListener(this);

        setTitle("S'inscrire");
    }

    @Override
    public void onClick(View v) {
        EditText email = (EditText) findViewById(R.id.emailText);
        EditText username = (EditText) findViewById(R.id.nameText);
        EditText password = (EditText) findViewById(R.id.pwdText);

        boolean emptyMail = TextUtils.isEmpty(email.getText());
        boolean emptyUsername = TextUtils.isEmpty(username.getText());
        boolean emptyPwd = TextUtils.isEmpty(password.getText());

        if(!emptyMail && !emptyPwd && !emptyUsername){
            userHelper = new UserHelper(getApplicationContext());
            User user = new User();
            user.setEmail(email.getText().toString());
            user.setRealm(username.getText().toString());
            user.setPassword(password.getText().toString());

            userHelper.create(user, this);
        } else {
            Toast.makeText(this, "Erreur", Toast.LENGTH_LONG);
        }

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {

    }

    @Override
    public void isUpdated(boolean isUpdated) {

    }

    @Override
    public void isDeleted(boolean isDeleted) {

    }

    @Override
    public void isCreated(boolean user) {
        if(user){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void gettingFilms(ArrayList<Film> films) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
