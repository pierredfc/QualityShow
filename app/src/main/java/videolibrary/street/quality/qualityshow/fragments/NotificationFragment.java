package videolibrary.street.quality.qualityshow.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.SettingsActivity;

/**
 * Created by Pierre on 08/11/2015.
 */
public class NotificationFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private View rootView;

    private Switch newepisode_switch;
    private Switch sound_switch;
    private Switch vibrate_switch;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        setSwitchs();

        ((SettingsActivity) getActivity()).getSupportActionBar().setTitle(R.string.notifications);
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(getString(R.string.notification_prefs), Context.MODE_PRIVATE);

        switch (buttonView.getId()) {
            case R.id.notification_switch:
                prefs.edit().putBoolean("enable", isChecked).apply();
                break;
            case R.id.notification_sound_switch:
                prefs.edit().putBoolean("sound", isChecked).apply();
                break;
            case R.id.notification_vibrate_switch:
                prefs.edit().putBoolean("vibrate", isChecked).apply();
                break;
            default:
                break;
        }
    }

    private void setSwitchs() {
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(getString(R.string.notification_prefs), Context.MODE_PRIVATE);

        newepisode_switch = (Switch) rootView.findViewById(R.id.notification_switch);
        newepisode_switch.setOnCheckedChangeListener(this);
        newepisode_switch.setChecked(prefs.getBoolean("enable", false));

        sound_switch = (Switch) rootView.findViewById(R.id.notification_sound_switch);
        sound_switch.setOnCheckedChangeListener(this);
        sound_switch.setChecked(prefs.getBoolean("sound", false));

        vibrate_switch = (Switch) rootView.findViewById(R.id.notification_vibrate_switch);
        vibrate_switch.setOnCheckedChangeListener(this);
        vibrate_switch.setChecked(prefs.getBoolean("vibrate", false));
    }
}
