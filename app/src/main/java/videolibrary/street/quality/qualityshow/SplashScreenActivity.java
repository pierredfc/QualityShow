package videolibrary.street.quality.qualityshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.utils.CitationHelper;

public class SplashScreenActivity extends Activity{
    TextView citation;
    CitationHelper citationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Intent intentLoginActivity = new Intent(this, LoginActivity.class);
        //startActivity(intentLoginActivity);

        this.citation = (TextView) findViewById(R.id.citation);
        this.citationHelper = new CitationHelper();
        setCitation(this.citationHelper.getCitation());


        if(isNetworkConnected()){
            nextActivity();
        } else {
            //mainActivity();
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

}
