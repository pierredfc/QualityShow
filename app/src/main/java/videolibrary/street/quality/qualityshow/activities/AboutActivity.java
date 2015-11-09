package videolibrary.street.quality.qualityshow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.ui.utils.DrawerMenuUtils;


public class AboutActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) this.findViewById(R.id.about_toolbar);
        this.setSupportActionBar(toolbar);

        new DrawerMenuUtils(savedInstanceState, this, toolbar);

        setTitle(getString(R.string.about));
    }
}
