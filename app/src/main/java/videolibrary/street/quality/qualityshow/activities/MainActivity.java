package videolibrary.street.quality.qualityshow.activities;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.AlarmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.fragments.HomeFragment;
import videolibrary.street.quality.qualityshow.listeners.CalendarListener;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.receivers.NewEpisodeReceiver;
import videolibrary.street.quality.qualityshow.services.EpisodeNotificationService;
import videolibrary.street.quality.qualityshow.ui.utils.DrawerMenuUtils;
import videolibrary.street.quality.qualityshow.utils.AlarmPreferences;
import videolibrary.street.quality.qualityshow.utils.CalendarUtils;
import videolibrary.street.quality.qualityshow.utils.Constants;
import videolibrary.street.quality.qualityshow.utils.SearchPreferences;

public class MainActivity extends AppCompatActivity implements UserListener, ClickListener, CalendarListener, RequestListener, DialogInterface.OnClickListener, AlarmListener {

    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private HomeFragment homeFragment;
    public DrawerMenuUtils drawer;
    AlertDialog closeDialog;

    private SearchPreferences searchPreferences;
    private AlarmPreferences alarmPreferences;
    private PendingIntent mServicePendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchPreferences = new SearchPreferences(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = new DrawerMenuUtils(savedInstanceState, this, toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setSuggestions(searchPreferences.getSearchPreferences());
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = parent.getItemAtPosition(position).toString();
                searchView.closeSearch();
                startSearchActivity(query);
            }
        });

        alarmPreferences = new AlarmPreferences(this);

        homeFragment = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void startSearchActivity(String query) {
        searchPreferences.setSearchPreferences(query);
        searchView.setSuggestions(searchPreferences.getSearchPreferences());

        Intent intent = new Intent(QualityShowApplication.getContext(), SearchActivity.class);
        Bundle extras = new Bundle();
        extras.putString(getString(R.string.query), query);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void launchNotificationService(){
        final Intent episodeNotificationService = new Intent(this, EpisodeNotificationService.class);

        final Calendar cal = Calendar.getInstance();
        mServicePendingIntent = PendingIntent.getService(this, 0, episodeNotificationService, 0);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), Constants.POLLING_DELAY, mServicePendingIntent);

    }

    @Override
    public void setSeries(ArrayList<Serie> series) {
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(getString(R.string.notification_prefs), Context.MODE_PRIVATE);

        for (Serie serie : series) {
            for (Saison saison : serie.getSaisons()) {
                int aired_episodes = saison.getAired_episodes();
                List<Episode> episodes = saison.getEpisodes();
                for (int i = aired_episodes; i < episodes.size(); i++) {
                    if(CalendarUtils.getDayDiff(episodes.get(i).getFirst_aired()) >= 0){
                        String[] tokens = episodes.get(i).getFirst_aired().split("[-]");
                        int year = Integer.parseInt(tokens[0]);
                        int month = Integer.parseInt(tokens[1]);
                        int day = Integer.parseInt(tokens[2].split("[T]")[0]);
                        int id = episodes.get(i).getIds().get("trakt");
                        if(!alarmPreferences.isInAlarmPreferences(id)){
                            setAlarm(serie.getTitle(), id, year, month, day);
                            alarmPreferences.setAlarmPreferences(id);
                        }
                    }
                }
            }
        }
    }

    private void setAlarm(String serieName, int id, int year, int month, int day){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        int hour = 12;
        int minutes = 0;
        month -= 1;

        cal.set(year, month, day, hour, minutes);
        cal.add(Calendar.DATE, 1);
        Intent intent = new Intent(QualityShowApplication.getContext(), NewEpisodeReceiver.class);
        Bundle extras = new Bundle();
        extras.putString(Constants.NEW_EPISODE_RECEIVER, serieName);
        intent.putExtras(extras);

        PendingIntent operation = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), operation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_menu_about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Object item) {

        if (item instanceof Serie) {
            Intent intent = new Intent(this, ShowActivity.class);
            intent.putExtra("isMovie", false);
            if (QualityShowApplication.getUserHelper() != null) {
                intent.putExtra("isSearch", false);
                intent.putExtra("show", (int) ((Serie) item).getId());
            } else {
                intent.putExtra("show", (Serie) item);
            }

            startActivity(intent);
        }

        if (item instanceof Film) {

            Intent intent = new Intent(this, ShowActivity.class);
            intent.putExtra("isMovie", true);
            if (QualityShowApplication.getUserHelper() != null) {
                intent.putExtra("isSearch", false);
                intent.putExtra("show", (int) ((Film) item).getId());
            } else {
                intent.putExtra("show", (Film) item);
            }

            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        int entryCount = getFragmentManager().getBackStackEntryCount();
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else if (entryCount > 1) {
            getFragmentManager().popBackStack();
        } else {
            askLeaveOrLogout();
        }
    }

    private void askLeaveOrLogout() {
        closeDialog = makeCloseDialog();
        closeDialog.show();
    }

    private AlertDialog makeCloseDialog() {
        if (closeDialog != null) {
            return closeDialog;
        }

        closeDialog = new AlertDialog.Builder(this).create();
        closeDialog.setTitle(getString(R.string.dialog_title));
        closeDialog.setMessage(getString(R.string.dialog_message));
        closeDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_cancel_choice), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        if (QualityShowApplication.getUserHelper().getCurrentUser() != null) {
            closeDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.dialog_logout_choice), this);
        }

        closeDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_quit_choice), this);

        return closeDialog;
    }

    @Override
    public void onCalendarRequestReceived(List<String> response) {

    }

    @Override
    public void onResponseReceived(List<Object> response) {
        Intent intent = new Intent(this, ShowActivity.class);
        if (response.get(0) instanceof Serie) {
            intent.putExtra("isMovie", false);
            intent.putExtra("show", (Serie) response.get(0));
        } else if (response.get(0) instanceof Film) {
            intent.putExtra("isMovie", true);
            intent.putExtra("show", (Film) response.get(0));
        }
        startActivity(intent);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == AlertDialog.BUTTON_NEGATIVE) {
            if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
                super.onBackPressed();
            } else {
                QualityShowApplication.getUserHelper().logout(this);
            }
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {

    }

    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    @Override
    public void userIsCreated(boolean user) {

    }

    @Override
    public void userIsLogout() {
        finish();
    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void userIsRetrieved(User user) {

    }

    @Override
    public void onError(Throwable t) {

    }
}
