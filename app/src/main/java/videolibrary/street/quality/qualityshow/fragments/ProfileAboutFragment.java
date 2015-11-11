package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vlonjatg.progressactivity.ProgressActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;

/**
 * Created by Pierre on 09/11/2015.
 */
public class ProfileAboutFragment extends Fragment{

    public ProgressActivity rootView;
    private TextView emailView;
    private TextView usernameView;
    private TextView showsFollowedView;
    private TextView moviesSeenView;
    private User user;

    public static ProfileAboutFragment newInstance() {
        final ProfileAboutFragment profileAboutFragment = new ProfileAboutFragment();
        return profileAboutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ProgressActivity) inflater.inflate(R.layout.fragment_about_profile, container, false);

        emailView = (TextView) rootView.findViewById(R.id.about_profile_email_response);
        usernameView = (TextView) rootView.findViewById(R.id.about_profile_username_response);
        showsFollowedView = (TextView) rootView.findViewById(R.id.about_profile_shows_followed_response);
        moviesSeenView = (TextView) rootView.findViewById(R.id.about_profile_movies_seen_response);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        user = QualityShowApplication.getUserHelper().getCurrentUser();

        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
            user.setEmail("anonyme@qualityshow.fr");
        } else {
            ArrayList<Serie> series = user.getSeries();
            if(series != null){
                showsFollowedView.setText(Integer.toString(series.size()));
            }

            ArrayList<Film> films = user.getFilms();
            if(films != null){
                moviesSeenView.setText(Integer.toString(films.size()));
            }
        }

        emailView.setText(user.getEmail());
        usernameView.setText(user.getUsername());
    }
}
