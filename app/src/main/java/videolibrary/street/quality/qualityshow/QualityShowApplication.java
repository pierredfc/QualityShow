package videolibrary.street.quality.qualityshow;

import android.app.Application;
import android.content.Context;

import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;


public class QualityShowApplication extends Application {

    private static Context sContext;
    private static UserHelper userHelper;

    public void onCreate(){
        super.onCreate();

        // Keep a reference to the application context
        sContext = getApplicationContext();
        if(userHelper == null){
            userHelper = new UserHelper(sContext);
        }
    }

    // Used to access Context anywhere within the app
    public static Context getContext() {
        return sContext;
    }

    public static UserHelper getUserHelper(){
        return userHelper;
    }
}
