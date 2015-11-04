package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import videolibrary.street.quality.qualityshow.MainActivity;
import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.adapters.SearchAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
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

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        query = getArguments().getString("query");
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
        requestAsyncTask.execute(Requests.MOVIE_SEARCH, query);
    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if(response.size() == 0){
            no_result_foundView.setVisibility(View.VISIBLE);
        } else {
            searchAdapter = new SearchAdapter(response, (MainActivity) getActivity());
            resultsView.setAdapter(searchAdapter);
        }
    }


}
