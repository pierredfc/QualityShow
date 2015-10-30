package videolibrary.street.quality.qualityshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.UserRepository;
import videolibrary.street.quality.qualityshow.utils.CitationHelper;
import videolibrary.street.quality.qualityshow.utils.Constants;

public class SplashScreenActivity extends Activity implements UserListener{
    TextView citation;
    CitationHelper citationHelper;

    User user;
    AccessToken accessToken;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        this.citation = (TextView) findViewById(R.id.citation);
        this.citationHelper = new CitationHelper();
        setCitation(this.citationHelper.getCitation());

        userHelper = new UserHelper(getApplicationContext());
        userHelper.create("test", "test@test.fr", "test", "MR. test", this);
//        userHelper.login("string@string.fr", "string", (UserListener) this);


        if(isNetworkConnected()){
            nextActivity();
        } else {
            mainActivity();
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

    private void mainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
        this.userHelper.films(this.user, false, (UserListener) this);
    }

    @Override
    public void isUpdated(boolean isUpdated) {
    }

    @Override
    public void isDeleted(boolean isDeleted) {
    }

    @Override
    public void isCreated(boolean user) {
    }

    /**
     * Get user with all films include
     *
     * @param films List of films received
     */
    @Override
    public void gettingFilms(ArrayList<Film> films) {
        this.user.setFilms(films);
    }

    @Override
    public void onError(Throwable e) {
    }
}
