package videolibrary.street.quality.qualityshow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.*;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;

/**
 * Created by EdouardEtudiant on 06/11/2015.
 */
public class TabFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.list_item);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        //mAdapter = new ShowsAdapter(series)
        //recyclerView.setAdapter(mAdapter);

        return view;

    }

    //@Override
    //public void getSeries(ArrayList<Serie> series) {
    //    QualityShowApplication.getUserHelper().getCurrentUser().setSeries(series);
    //    showsAdapter = new ShowsAdapter(series, (MainActivity) getActivity());
    //    showsView.setAdapter(showsAdapter);
    //    this.showsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    //}
}
