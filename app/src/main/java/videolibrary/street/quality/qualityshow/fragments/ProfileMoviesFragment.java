package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ProfileActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;

/**
 * Created by Pierre on 09/11/2015.
 */
public class ProfileMoviesFragment extends Fragment implements FilmListener {

    public ProgressActivity rootView;
    private RecyclerView showsView;
    private TextView no_moviesView;

    private ShowsAdapter showsAdapter;

    public static ProfileMoviesFragment newInstance() {
        final ProfileMoviesFragment profileMoviesFragment = new ProfileMoviesFragment();
        return profileMoviesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ProgressActivity) inflater.inflate(R.layout.fragment_movies_profile, container, false);
        showsView = (RecyclerView) rootView.findViewById(R.id.movies_profile_recyclerView);
        this.showsView.setHasFixedSize(true);
        this.showsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        no_moviesView = (TextView) rootView.findViewById(R.id.no_movies);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (QualityShowApplication.getUserHelper().getCurrentUser() != null) {
            QualityShowApplication.getUserHelper().films(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
            rootView.showLoading();
        }
    }

    @Override
    public void filmIsAdded(Film film) {

    }

    @Override
    public void filmIsDeleted() {

    }

    @Override
    public void getFilms(ArrayList<Film> films) {
        if(films.size() == 0){
            no_moviesView.setVisibility(View.VISIBLE);
        } else {
            QualityShowApplication.getUserHelper().getCurrentUser().setFilms(films);
            showsAdapter = new ShowsAdapter(null, films, (ProfileActivity) getActivity());
            showsView.setAdapter(showsAdapter);
        }
        rootView.showContent();
    }

    @Override
    public void onError(Throwable t) {

    }
}
