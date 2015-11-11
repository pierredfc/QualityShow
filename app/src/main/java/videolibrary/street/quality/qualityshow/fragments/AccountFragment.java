package videolibrary.street.quality.qualityshow.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.LoginActivity;
import videolibrary.street.quality.qualityshow.activities.SettingsActivity;
import videolibrary.street.quality.qualityshow.activities.SignUpActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.User;


public class AccountFragment extends Fragment {

    private View rootView;
    private TextView emailView;
    private TextView usernameView;
    private TextView changePassword;
    private TextView socialNetwork;
    private ImageView twitterView;
    private Button logInButton;
    private Button signUpButton;
    private TextView or;

    private User user;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        emailView = (TextView) rootView.findViewById(R.id.settings_email_response);
        usernameView = (TextView) rootView.findViewById(R.id.settings_username_response);
        changePassword = (TextView) rootView.findViewById(R.id.settings_change_password);
        socialNetwork = (TextView) rootView.findViewById(R.id.settings_socail_network);
        or = (TextView) rootView.findViewById(R.id.or);
        twitterView = (ImageView) rootView.findViewById(R.id.twitter_icon);
        logInButton = (Button) rootView.findViewById(R.id.login_button);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QualityShowApplication.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton = (Button) rootView.findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QualityShowApplication.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        ((SettingsActivity) getActivity()).getSupportActionBar().setTitle(R.string.my_account);

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
            changePassword.setVisibility(View.GONE);
            socialNetwork.setVisibility(View.GONE);
            twitterView.setVisibility(View.GONE);
            or.setVisibility(View.VISIBLE);
            logInButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);

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
