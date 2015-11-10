package videolibrary.street.quality.qualityshow.utils;

/**
 * Created by elerion on 10/29/15.
 */
public class Constants {


    public static final Integer CACHED_SEARCHES = 15;

    public static class Log{
        public static final String TAG = "QualityShow";
        public static final String ERROR_MSG = "Error during : ";
    }

    public static class Drawer {
        public static String[] getDrawerList(){
            String[] items = new String[] { "Profil",
                    "Mon planning",
                    "Mes séries",
                    "Mes films",
                    "Réglages",
                    "About"
            };

            return items;
        }
    }

}
