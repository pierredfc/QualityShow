package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.AboutActivity;
import videolibrary.street.quality.qualityshow.activities.SettingsActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.User;


public class AccountFragment extends Fragment {
    View rootView;

    private TextView emailView;
    private TextView usernameView;

    private TextView changePassword;
    private ImageView twitterView;

    private User user;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        emailView = (TextView) rootView.findViewById(R.id.settings_email_response);
        usernameView = (TextView) rootView.findViewById(R.id.settings_username_response);
        changePassword = (TextView) rootView.findViewById(R.id.settings_change_password);
        twitterView = (ImageView) rootView.findViewById(R.id.twitter_icon);

        ((SettingsActivity) getActivity()).getSupportActionBar().setTitle(R.string.my_account);

        ((SettingsActivity) getActivity()).getDrawer().getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        ((SettingsActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        user = QualityShowApplication.getUserHelper().getCurrentUser();

        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
            user.setEmail("anonyme@qualityshow.fr");
        }

        emailView.setText(user.getEmail());
        usernameView.setText(user.getUsername());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
