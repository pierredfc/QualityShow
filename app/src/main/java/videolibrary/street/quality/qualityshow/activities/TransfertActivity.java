package videolibrary.street.quality.qualityshow.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.List;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 12/11/2015.
 */
public class TransfertActivity extends AppCompatActivity implements RequestListener{
    private Object show;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfert);
        toolbar = (Toolbar) findViewById(R.id.show_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        show = intent.getParcelableExtra("show");
        if (show instanceof Serie) {

            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.SERIE_FIND, String.valueOf(((Serie) show).getIds().get("slug")));

        }
        if (show instanceof Film) {
            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.MOVIE_FIND, String.valueOf(((Film) show).getIds().get("slug")));

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transfert_menu, menu);
        return true;
    }
    @Override
    public void onResponseReceived(List<Object> response) {
        if(response.size() > 0){
            if(response.get(0) instanceof  Serie) {
                Intent intent = new Intent(this, ShowActivity.class);
                intent.putExtra("isSearch", true);
                intent.putExtra("isMovie", false);
                intent.putExtra("show", (Serie) response.get(0));
                startActivity(intent);
            }
            else if(response.get(0) instanceof Film){
                Intent intent = new Intent(this, ShowActivity.class);
                intent.putExtra("isSearch", true);
                intent.putExtra("isMovie",true);
                intent.putExtra("show",(Film)response.get(0));
                startActivity(intent);
            }
        }

    }

    }
