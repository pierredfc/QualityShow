package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Fanart extends HashMap<String, String>{

    @Override
    public String get(Object key){
        String tmpKey = (String) key;
        switch (tmpKey){
            case "thumb":
                return this.values().toArray()[0].toString();
            case "medium":
                return this.values().toArray()[1].toString();
            case "full":
                return this.values().toArray()[2].toString();
            default:
                return null;
        }
    }
}
