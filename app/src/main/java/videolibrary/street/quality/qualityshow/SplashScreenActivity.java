package videolibrary.street.quality.qualityshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import com.strongloop.android.loopback.AccessToken;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.FilmHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.utils.CitationHelper;

public class SplashScreenActivity extends Activity{

    TextView citation;
    CitationHelper citationHelper;

    User user;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        this.citation = (TextView) findViewById(R.id.citation);
        this.citationHelper = new CitationHelper();
        setCitation(this.citationHelper.getCitation());

        UserHelper users = new UserHelper(getApplicationContext());
        users.login("string@string.fr", "string");

        FilmHelper filmHelper = new FilmHelper(getApplicationContext());
        filmHelper.getAllFilms();


        if(isNetworkConnected()){
           nextActivity();
        } else {
            //@TODO
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void setCitation(String citation){
        this.citation.setText(citation);
    }

    private void nextActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
