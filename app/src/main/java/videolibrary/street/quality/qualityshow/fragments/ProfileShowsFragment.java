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
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;

/**
 * Created by Pierre on 09/11/2015.
 */
public class ProfileShowsFragment extends Fragment implements SerieListener {

    public ProgressActivity rootView;
    private RecyclerView showsView;
    private TextView no_showsView;
    private ShowsAdapter showsAdapter;

    public static ProfileShowsFragment newInstance() {
        final ProfileShowsFragment profileShowsFragment = new ProfileShowsFragment();
        return profileShowsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ProgressActivity) inflater.inflate(R.layout.fragment_shows_profile, container, false);
        showsView = (RecyclerView) rootView.findViewById(R.id.shows_profile_recyclerView);
        this.showsView.setHasFixedSize(true);
        this.showsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        no_showsView = (TextView) rootView.findViewById(R.id.no_series);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (QualityShowApplication.getUserHelper().getCurrentUser() != null) {
            QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
            rootView.showLoading();
        }
    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        if(series.size() == 0){
            no_showsView.setVisibility(View.VISIBLE);
        } else {
            QualityShowApplication.getUserHelper().getCurrentUser().setSeries(series);
            showsAdapter = new ShowsAdapter(series, null, (ProfileActivity) getActivity());
            showsView.setAdapter(showsAdapter);
        }
        rootView.showContent();
    }

    @Override
    public void onError(Throwable t) {

    }
}
