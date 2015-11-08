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

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.repositories.EpisodeRepository;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;
import videolibrary.street.quality.qualityshow.api.user.repositories.SaisonRepository;
import videolibrary.street.quality.qualityshow.api.user.repositories.SerieRepository;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanMovieItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanShowItem;

public class Requests {
    public static final String HOST = "http://de-coster.fr:4000/api";
    public static final String MOVIES_PATH = "Movies";
    public static final String SERIES_PATH = "Series";
    public static final String MOVIE_SERIE_SEARCH = "Movie_serie_search";
    public static final String MOVIE_SEARCH = "Movie_search";
    public static final String SERIE_SEARCH = "Serie_search";
    public static final String MOVIE_FIND = "Movie_find";
    public static final String SERIE_FIND = "Serie_find";
    public static final String MOVIE_POPULAR = "Movie_popular";
    public static final String MOVIE_TRENDING = "Movie_trending";
    public static final String SERIE_POPULAR = "Serie_popular";
    public static final String SERIE_TRENDING = "Serie_trending";
    public static final String POPULAR_PATH = "popular";
    public static final String TRENDING_PATH = "trending";
    public static final String SERIE_SEASONS = "Serie_seasons";
    public static final String SEASONS_PATH = "seasons";
    public static final String SEASON_PATH = "season";
    public static final String CALENDAR_HOST = "https://api-v2launch.trakt.tv/calendars/all/shows";

    public static List<Object> search(String mode, String toSearch) {
        try {
            String request = null;

            if(toSearch.contains(" ")){
                toSearch = toSearch.replace(" ", "+");
            }

            switch (mode) {
                case MOVIE_SERIE_SEARCH:
                    request = HOST + "/Search?query=" + toSearch;
                    break;
                case MOVIE_SEARCH:
                    request = HOST + "/" + MOVIES_PATH + "/search?movie=" + toSearch;
                    break;
                case SERIE_SEARCH:
                    request = HOST + "/" + SERIES_PATH + "/search?serie=" + toSearch;
                    break;
                case MOVIE_FIND:
                    request = HOST + "/" + MOVIES_PATH + "/find?movie=" + toSearch;
                    break;
                case SERIE_FIND:
                    request = HOST + "/" + SERIES_PATH + "/find?serie=" + toSearch;
                    break;
                case MOVIE_POPULAR:
                    request = HOST + "/" + MOVIES_PATH + "/" + POPULAR_PATH;
                    break;
                case MOVIE_TRENDING:
                    request = HOST + "/" + MOVIES_PATH + "/" + TRENDING_PATH;
                    break;
                case SERIE_POPULAR:
                    request = HOST + "/" + SERIES_PATH + "/" + POPULAR_PATH;
                    break;
                case SERIE_TRENDING:
                    request = HOST + "/" + SERIES_PATH + "/" + TRENDING_PATH;
                    break;
                case SERIE_SEASONS:
                    request = HOST + "/" + SERIES_PATH + "/" + toSearch + "/" + SEASONS_PATH;
                    break;
            }
            Log.d("Request", request);

            final HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            final int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String jsonStr = convertStreamToString(connection.getInputStream());
                Log.d("Requests", jsonStr);

                List<Object> items = new ArrayList<>();

                if (mode == SERIE_FIND || mode == MOVIE_FIND) {
                    JSONObject obj = new JSONObject(jsonStr);
                    obj.put("poster", obj.getJSONObject("images").getJSONObject("poster"));
                    obj.put("fanart", obj.getJSONObject("images").getJSONObject("fanart"));
                    if (mode == SERIE_FIND) {
                        Object item = new SerieRepository().createObject(JsonUtil.fromJson(obj));
                        items.add(item);
                    }
                    if (mode == MOVIE_FIND) {
                        Object item = new FilmRepository().createObject(JsonUtil.fromJson(obj));
                        items.add(item);
                    }
                    return items;
                }

                JSONArray jsonArray = new JSONArray(jsonStr);

                Log.d("Requests", "JSONArray length: " + jsonArray.length());

                switch (mode) {
                    case SERIE_SEASONS: {
                        SaisonRepository repo = new SaisonRepository();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            jsonObject.put("poster", jsonObject.getJSONObject("images").getJSONObject("poster"));
                            Object item = repo.createObject(JsonUtil.fromJson(jsonObject));
                            items.add(item);
                        }
                    }
                        break;
                    case SERIE_POPULAR:
                    case MOVIE_POPULAR: {
                        FilmRepository repo = new FilmRepository();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            jsonObject.put("poster", jsonObject.getJSONObject("images").getJSONObject("poster"));
                            jsonObject.put("fanart", jsonObject.getJSONObject("images").getJSONObject("fanart"));
                            Object item = repo.createObject(JsonUtil.fromJson(jsonObject));
                            items.add(item);
                        }
                    }
                        break;
                    case MOVIE_TRENDING:
                    case MOVIE_SEARCH: {
                        FilmRepository repo = new FilmRepository();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject tmpObj = jsonObject.getJSONObject("movie");
                            tmpObj.put("poster", tmpObj.getJSONObject("images").getJSONObject("poster"));
                            tmpObj.put("fanart", tmpObj.getJSONObject("images").getJSONObject("fanart"));
                            Object item = repo.createObject(JsonUtil.fromJson(tmpObj));
                            items.add(item);
                        }
                    }
                        break;
                    case SERIE_TRENDING:
                    case SERIE_SEARCH: {
                        SerieRepository repo = new SerieRepository();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject tmpObj = jsonObject.getJSONObject("show");
                            tmpObj.put("poster", tmpObj.getJSONObject("images").getJSONObject("poster"));
                            tmpObj.put("fanart", tmpObj.getJSONObject("images").getJSONObject("fanart"));
                            Object item = repo.createObject(JsonUtil.fromJson(tmpObj));
                            items.add(item);
                        }

                    }
                    break;
                    case MOVIE_SERIE_SEARCH: {
                        SerieRepository srepo = new SerieRepository();
                        FilmRepository frepo= new FilmRepository();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject tmpObj = null;
                            if (jsonObject.has("show")) {
                                tmpObj = jsonObject.getJSONObject("show");
                            }
                            if (jsonObject.has("movie")) {
                                tmpObj = jsonObject.getJSONObject("movie");
                            }
                            tmpObj.put("poster", tmpObj.getJSONObject("images").getJSONObject("poster"));
                            tmpObj.put("fanart", tmpObj.getJSONObject("images").getJSONObject("fanart"));
                            Object item = null;
                            if (jsonObject.has("show")) {
                                item = srepo.createObject(JsonUtil.fromJson(tmpObj));

                            }
                            if (jsonObject.has("movie")) {
                                item = frepo.createObject(JsonUtil.fromJson(tmpObj));
                            }
                            items.add(item);
                        }
                    }
                }

                return items;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
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

    public static List<String> calendarSearch(String show, String date, Integer days){
        try{
            String request = null;
            request = HOST + "/Calendar?type_request=" + show + "&start_date=" + date + "&day=" + days;
            Log.d("Request", request);

            final HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            final int responseCode = connection.getResponseCode();

            if(responseCode == 200){
                String jsonStr = convertStreamToString(connection.getInputStream());

            }

        } catch (Exception e){
            Log.e("calendarSearch", e.getMessage());
        }

        return null;
    }
    public static List<Object> SeasonSearch(String idserie,String numberseason){
        try{
            String request = null;
            request = HOST + "/"+SERIES_PATH +"/" + idserie + "/"+SEASON_PATH +"/"+numberseason;
            Log.d("Request", request);

            final HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            final int responseCode = connection.getResponseCode();

            if(responseCode == 200){
                String jsonStr = convertStreamToString(connection.getInputStream());
                Log.d("Requests", jsonStr);
                JSONArray jsonArray = new JSONArray(jsonStr);
                List<Object> items = new ArrayList<>();

                Log.d("Requests", "JSONArray length: " + jsonArray.length());
                EpisodeRepository repo = new EpisodeRepository();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    jsonObject.put("screenshot", jsonObject.getJSONObject("images").getJSONObject("screenshot"));
                    Episode item = repo.createObject(JsonUtil.fromJson(jsonObject));
                    items.add(item);
                }
                return items;
            }

        } catch (Exception e){
            Log.e("seasonsearch", e.getMessage());
        }
        return null;
    }
}