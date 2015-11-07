package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.SearchActivity;
import videolibrary.street.quality.qualityshow.ui.adapters.SearchAdapter;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.ui.utils.DividerItemDecoration;
import videolibrary.street.quality.qualityshow.utils.Requests;


public class SearchFragment extends Fragment implements RequestListener {
    private View rootView;
    private RecyclerView resultsView;

    private String query;
    private SearchAdapter searchAdapter;
    private TextView no_result_foundView;

    public static SearchFragment newInstance(String query) {
        final SearchFragment searchFragment = new SearchFragment();
        final Bundle arguments = new Bundle();
        arguments.putString("query", query);
        searchFragment.setArguments(arguments);

        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        resultsView = (RecyclerView) rootView.findViewById(R.id.search_resultsView);
        no_result_foundView = (TextView) rootView.findViewById(R.id.no_result_found);
        no_result_foundView.setVisibility(View.GONE);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(QualityShowApplication.getContext());
        resultsView.setLayoutManager(layoutManager);

        query = getArguments().getString("query");
        ((SearchActivity) getActivity()).getSupportActionBar().setTitle(query);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
        requestAsyncTask.execute(Requests.MOVIE_SERIE_SEARCH, query);
    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if(response.size() == 0){
            no_result_foundView.setVisibility(View.VISIBLE);
        } else {
            searchAdapter = new SearchAdapter(response, (SearchActivity) getActivity());
            resultsView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            resultsView.setAdapter(searchAdapter);
        }
    }


}
