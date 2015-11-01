package videolibrary.street.quality.qualityshow;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.utils.CitationHelper;

public class StartActivity extends Activity implements View.OnClickListener, UserListener {

    TextView citation;
    CitationHelper citationHelper;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.citation = (TextView) findViewById(R.id.start_citation);
        this.citationHelper = new CitationHelper();
        this.citation.setText(this.citationHelper.getCitation());

        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        String email = prefs.getString("Email", "");
        String pwd = prefs.getString("Password", "");

        if (!email.isEmpty() && !pwd.isEmpty()) {
            QualityShowApplication.getUserHelper().login(email, pwd, this);
        } else {
            stopProgressBar();
            findViewById(R.id.no_account_button).setOnClickListener(this);
            findViewById(R.id.sign_up_button).setOnClickListener(this);
            findViewById(R.id.sign_in).setOnClickListener(this);
        }
     }


    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            Button button = (Button) v;

            switch(button.getId()){
                case R.id.no_account_button:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.sign_up_button:
                    startActivity(new Intent(this, SignUpActivity.class));
                    break;
                default:
                    break;
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {
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

    private void stopProgressBar(){
        progressBar = (ProgressBar) findViewById(R.id.start_progressBar);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable t) {
        stopProgressBar();

        findViewById(R.id.no_account_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
    }
}
