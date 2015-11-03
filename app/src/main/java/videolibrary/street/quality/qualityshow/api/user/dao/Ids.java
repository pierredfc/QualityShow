package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

import java.util.HashMap;

/**
 * Created by elerion on 1/3/15.
 */
public class Ids extends HashMap<String, String>{

    @Override
    public String get(Object key){
        String tmpKey = (String) key;
        switch (tmpKey){
            case "tvdb":
                return this.values().toArray()[0].toString();
            case "tmdb":
                return this.values().toArray()[1].toString();
            case "slug":
                return this.values().toArray()[2].toString();
            case "imdb":
                return this.values().toArray()[3].toString();
            case "trakt":
                return this.values().toArray()[4].toString();
            case "tvrage":
                return this.values().toArray()[5].toString();
            default:
                return null;
        }
    }
}
