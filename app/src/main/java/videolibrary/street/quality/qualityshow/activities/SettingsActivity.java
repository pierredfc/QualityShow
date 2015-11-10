package videolibrary.street.quality.qualityshow.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.fragments.AccountFragment;
import videolibrary.street.quality.qualityshow.fragments.NotificationFragment;
import videolibrary.street.quality.qualityshow.ui.utils.DrawerMenuUtils;


public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerMenuUtils drawer;

    private TextView account;
    private TextView notifications;

    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.settings);

        drawer = new DrawerMenuUtils(savedInstanceState, this, toolbar);

        account = (TextView) findViewById(R.id.settings_account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountFragment = new AccountFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.settings_frame_container, accountFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        notifications = (TextView) findViewById(R.id.settings_notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationFragment = new NotificationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.settings_frame_container, notificationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public Drawer getDrawer() {
        return drawer.getDrawer();
    }
}
