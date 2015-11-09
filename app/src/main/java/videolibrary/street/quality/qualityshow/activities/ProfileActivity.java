package videolibrary.street.quality.qualityshow.activities;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.fragments.ProfileFragment;
import videolibrary.street.quality.qualityshow.ui.utils.DrawerMenuUtils;

/**
 * Created by EdouardEtudiant on 08/11/2015.
 */
public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerMenuUtils drawer;

    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");

        drawer = new DrawerMenuUtils(savedInstanceState, this, toolbar);

        profileFragment= new ProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //transaction.add(R.id.profile_frame_container, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
