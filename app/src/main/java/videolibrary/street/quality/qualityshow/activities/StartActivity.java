package videolibrary.street.quality.qualityshow.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.strongloop.android.loopback.AccessToken;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.helpers.CitationHelper;

public class StartActivity extends Activity implements View.OnClickListener, UserListener {

    TextView citation;
    CitationHelper citationHelper;
    ProgressActivity progressActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.progressActivity = (ProgressActivity) findViewById(R.id.activity_start);
        this.citation = (TextView) findViewById(R.id.start_citation);
        this.citationHelper = new CitationHelper();
        this.citation.setText(this.citationHelper.getCitation());

        if(QualityShowApplication.getUserHelper().getCurrentUser() != null) {
            progressActivity.showLoading();
        }

        QualityShowApplication.getUserHelper().retrieveRegisteredUser(this);
        findViewById(R.id.no_account_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
     }


    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            Button button = (Button) v;

            switch(button.getId()){
                case R.id.no_account_button:
                    startActivity(new Intent(this, ExploreActivity.class));
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
    public void isLogged(AccessToken accessToken, User user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void userIsRetrieved(User user) {
        if(user == null){
            progressActivity.showContent();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onError(Throwable t) {
        progressActivity.showContent();
        findViewById(R.id.no_account_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
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
}
