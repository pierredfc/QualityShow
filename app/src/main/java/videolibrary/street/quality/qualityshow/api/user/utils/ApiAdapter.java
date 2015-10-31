package videolibrary.street.quality.qualityshow.api.user.utils;

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
