package videolibrary.street.quality.qualityshow.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;

/**
 * Created by Pierre on 12/11/2015.
 */
public class AlarmPreferences {
    Activity activity;

    public AlarmPreferences(Activity activity){
        this.activity = activity;
    }

    public void setAlarmPreferences(Integer newEpisodeID){
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(activity.getString(R.string.alarm_prefs), Context.MODE_PRIVATE);
        ArrayList<Integer> searches;

        Gson gson = new Gson();
        String json = prefs.getString(activity.getString(R.string.alarm_prefs_queue), "");
        if(json == ""){
            searches = new ArrayList<Integer>();
            searches.add(newEpisodeID);
        } else {
            searches = gson.fromJson(json, ArrayList.class);
            searches.add(newEpisodeID);
        }

        String searchesJson = gson.toJson(searches);
        prefs.edit().putString(activity.getString(R.string.alarm_prefs_queue), searchesJson).apply();
    }

    public ArrayList<Integer> getAlarmPreferences(){
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(activity.getString(R.string.alarm_prefs), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(activity.getString(R.string.alarm_prefs_queue), "");
        ArrayList<Integer> obj = gson.fromJson(json, ArrayList.class);

        return obj;
    }

    public boolean isInAlarmPreferences(int id){
        ArrayList<Integer> prefs = getAlarmPreferences();

        if(prefs == null || prefs.isEmpty()){
            return false;
        }

        if(prefs.contains((double) id)){
            return true;
        }

        return false;
    }

    public void deleteAlarmPreferences(){
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(activity.getString(R.string.alarm_prefs), Context.MODE_PRIVATE);
        prefs.edit().remove(activity.getString(R.string.alarm_prefs_queue)).apply();
    }
}
