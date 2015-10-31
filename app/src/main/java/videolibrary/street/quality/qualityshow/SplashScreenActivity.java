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

import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.ApiListeners;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;
import videolibrary.street.quality.qualityshow.api.user.repositories.UserRepository;
import videolibrary.street.quality.qualityshow.utils.CitationHelper;
import videolibrary.street.quality.qualityshow.utils.Constants;

public class SplashScreenActivity extends Activity implements ApiListeners{

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
        userHelper.login("string@string.fr", "string", (UserListener) this);




        if(isNetworkConnected()){
//           nextActivity();
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

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("title", "test");
        userHelper.addFilm(this.user, new FilmRepository().createObject(params), this);
    }

    /**
     * Call when update is done
     *
     * @param isUpdated Say if the user is updated or not
     */
    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    /**
     * Call when delete is done
     *
     * @param isDeleted Say if the user is deleted or not
     */
    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    /**
     * Say if the user is created or not
     *
     * @param user boolean User we have created
     */
    @Override
    public void userIsCreated(boolean user) {

    }

    @Override
    public void categorieIsAdded(Category category) {

    }

    @Override
    public void getCategories(ArrayList<Category> categories) {

    }

    @Override
    public void categorieIsDeleted() {

    }

    @Override
    public void saisonIsAdded(Saison saison) {

    }

    @Override
    public void getSaisons(ArrayList<Saison> saisons) {

    }

    @Override
    public void saisonIsDeleted() {

    }

    @Override
    public void episodeIsAdded(Episode episode) {

    }

    @Override
    public void getEpisodes(ArrayList<Episode> episodes) {

    }

    @Override
    public void episodeIsDelete() {

    }

    /**
     * Call when an action was not executed correctly
     *
     * @param t Exception return by action
     */
    @Override
    public void onError(Throwable t) {

    }


    @Override
    public void filmIsAdded(Film film) {
        userHelper.deleteFilm(this.user, (int) film.getId(), this);
    }

    @Override
    public void filmIsDeleted() {
        Log.d(Constants.Log.TAG, "End of films Test");
    }

    @Override
    public void getFilms(ArrayList<Film> films) {

    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {

    }
}
