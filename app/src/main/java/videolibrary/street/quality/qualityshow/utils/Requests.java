package videolibrary.street.quality.qualityshow.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.strongloop.android.remoting.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.repositories.SerieRepository;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanMovieItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanShowItem;

public class Requests {
    public static final String HOST = "http://de-coster.fr:4000/api";
    public static final String MOVIES_PATH = "Movies";
    public static final String SERIES_PATH = "Series";
    public static final String MOVIE_SEARCH = "Movie_search";
    public static final String SERIE_SEARCH = "Serie_search";

    public static List<Serie> search(String mode, String toSearch) {
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
                String jsonStr = convertStreamToString(connection.getInputStream());
                Log.d("Requests", jsonStr);
                JSONArray jsonArray = new JSONArray(jsonStr);
                SerieRepository repo = new SerieRepository();
                List<Serie> series = new ArrayList<>();
                Log.d("Requests", "JSONArray length: " + jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    JSONObject tmpSerie = jsonObject.getJSONObject("show");
                    tmpSerie.put("poster", tmpSerie.getJSONObject("images").getJSONObject("poster"));
                    tmpSerie.put("fanart", tmpSerie.getJSONObject("images").getJSONObject("fanart"));

                    Serie serie = repo.createObject(JsonUtil.fromJson(tmpSerie));

                    series.add(serie);
                }

                return series;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
