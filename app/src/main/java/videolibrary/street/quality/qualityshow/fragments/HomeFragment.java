package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SerieListener, FilmListener {

    public SwipeRefreshLayout rootView;
    private RecyclerView showsView;

    boolean userConnected;

    private ShowsAdapter showsAdapter;

    private List<Serie> userSerie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_home, container, false);
        showsView = (RecyclerView) rootView.findViewById(R.id.show_listView);
        this.showsView.setHasFixedSize(true);
        this.showsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        checkUserConnected();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.agenda);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(userConnected){
            //QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
            QualityShowApplication.getUserHelper().films(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
        }
    }

    private void checkUserConnected(){
        if(QualityShowApplication.getUserHelper().getCurrentUser() == null){
            userConnected = false;
        } else {
            userConnected = true;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        QualityShowApplication.getUserHelper().getCurrentUser().setSeries(series);
        showsAdapter = new ShowsAdapter(series, null, (MainActivity) getActivity());
        showsView.setAdapter(showsAdapter);
    }

    @Override
    public void filmIsAdded(Film film) {

    }

    @Override
    public void filmIsDeleted() {

    }

    @Override
    public void getFilms(ArrayList<Film> films) {
        QualityShowApplication.getUserHelper().getCurrentUser().setFilms(films);
        showsAdapter = new ShowsAdapter(null, films, (MainActivity) getActivity());
        showsView.setAdapter(showsAdapter);
    }

    @Override
    public void onError(Throwable t) {

    }
}
