package videolibrary.street.quality.qualityshow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startActivity(new Intent(this, SearchActivity.class));
     }

    public void LaunchSignInActivity(){

    }

}
