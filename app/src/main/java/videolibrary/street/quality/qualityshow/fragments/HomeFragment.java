package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.MainActivity;
import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.adapters.PlanningAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SerieListener {

    public SwipeRefreshLayout rootView;
    private RecyclerView showsView;

    private PlanningAdapter showsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.home_fragment, container, false);
        showsView = (RecyclerView) rootView.findViewById(R.id.show_listView);

        final GridLayoutManager layoutManager = new GridLayoutManager(QualityShowApplication.getContext(), 3);
        this.showsView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), false, this);
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
        Toast.makeText(getActivity(), "Nombre de séries: " + Integer.toString(series.size()), Toast.LENGTH_SHORT).show();
        QualityShowApplication.getUserHelper().getCurrentUser().setSeries(series);
        showsAdapter = new PlanningAdapter(series, (MainActivity) getActivity());
        showsView.setAdapter(showsAdapter);
    }

    @Override
    public void onError(Throwable t) {

    }
}
