package videolibrary.street.quality.qualityshow.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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


public class LoginActivity extends Activity implements UserListener, View.OnClickListener {

    ProgressActivity progressActivity;

    private View.OnClickListener errorClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        errorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        progressActivity = (ProgressActivity) findViewById(R.id.login_progress);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        setTitle(getString(R.string.sign_in_activity));
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
    public void isLogged(AccessToken accessToken, User user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(Throwable t) {
        progressActivity.showError(getDrawable(R.drawable.ic_info_outline), getString(R.string.internet_error), getString(R.string.internet_error_msg), getString(R.string.tryagain), errorClickListener);
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
            progressActivity.showLoading();
            QualityShowApplication.getUserHelper().login(mailEditable.toString().trim(), pwdEditable.toString(), this);
        } else {
            Toast.makeText(this, getString(R.string.fill_out), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

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
}
