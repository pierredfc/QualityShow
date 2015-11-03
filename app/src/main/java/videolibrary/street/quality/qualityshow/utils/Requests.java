package videolibrary.street.quality.qualityshow.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanMovieItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanShowItem;

public class Requests {
    public static final String HOST = "http://de-coster.fr:4000/api";
    public static final String MOVIES_PATH = "Movies";
    public static final String SERIES_PATH = "Series";
    public static final String MOVIE_SEARCH = "Movie_search";
    public static final String SERIE_SEARCH = "Serie_search";

    public static List<BeanItem> search(String mode, String toSearch) {
        try {
            String request = null;
            switch (mode) {
                case MOVIE_SEARCH:
                    request = HOST + "/" + MOVIES_PATH + "/search?movie=" + toSearch;
                    break;
                case SERIE_SEARCH:
                    request = HOST + "/" + SERIES_PATH + "/search?serie=" + toSearch;
                    break;
            }
            Log.d("Request", request);

            // Create the HTTP Get request to Twitter servers
            final HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            final int responseCode = connection.getResponseCode();

            // If success
            if (responseCode == 200) {
                Type typeList = null;
                switch (mode) {
                    case MOVIE_SEARCH:
                        typeList = new TypeToken<ArrayList<BeanMovieItem>>(){}.getType();
                        break;
                    case SERIE_SEARCH:
                        typeList = new TypeToken<ArrayList<BeanShowItem>>(){}.getType();
                        break;
                }
                return new Gson().fromJson(new com.google.gson.stream.JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8")), typeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
