package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;

import com.strongloop.android.loopback.RestAdapter;

/**
 * Created by elerion on 10/26/15.
 */
public class ApiAdapter extends RestAdapter {

    public ApiAdapter(Context context, String url) {
        super(context, url);
    }
}
