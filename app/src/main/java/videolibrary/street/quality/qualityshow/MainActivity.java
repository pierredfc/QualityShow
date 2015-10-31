package videolibrary.street.quality.qualityshow;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

public class MainActivity extends Activity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toolbar = (Toolbar) findViewById(R.id.main_toolBar);
        this.setActionBar(this.toolbar);
        setToolbar();
    }


    private void setToolbar(){

    }
}
