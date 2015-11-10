package videolibrary.street.quality.qualityshow.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.Queue;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;


public class SearchPreferences {

    Activity activity;

    public SearchPreferences(Activity activity){
        this.activity = activity;
    }

    public void setSearchPreferences(String newSearch){
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(activity.getString(R.string.search_prefs), Context.MODE_PRIVATE);
        Queue<String> searches;
        boolean exist = false;

        Gson gson = new Gson();
        String json = prefs.getString(activity.getString(R.string.search_prefs_queue), "");
        if(json == ""){
            searches = new LinkedList<String>();
            searches.add(newSearch);
        } else {
            searches = gson.fromJson(json, LinkedList.class);
            for(String s: searches){
                if(s.equals(newSearch)) {
                    exist = true;
                    break;
                }
            }

            if(!exist){
                if (searches.size() == Constants.CACHED_SEARCHES){
                    searches.poll();
                }
                searches.add(newSearch);
            }
        }

        String searchesJson = gson.toJson(searches);
        prefs.edit().putString(activity.getString(R.string.search_prefs_queue), searchesJson).apply();
    }

    public String[] getSearchPreferences(){
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(activity.getString(R.string.search_prefs), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(activity.getString(R.string.search_prefs_queue), "");
        LinkedList<String> obj = gson.fromJson(json, LinkedList.class);

        if(obj == null){
            return null;
        }

        return  obj.toArray(new String[obj.size()]);
    }


    public void deleteSearchPreferences(){
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(activity.getString(R.string.search_prefs), Context.MODE_PRIVATE);
        prefs.edit().remove(activity.getString(R.string.search_prefs_queue)).apply();
    }
}
