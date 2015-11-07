package videolibrary.street.quality.qualityshow.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;


/**
 * Created by Pierre on 06/11/2015.
 */
public class CustomSearchView extends SearchView {

    Activity activiy;

    public CustomSearchView(Context context) {
        super(context);
        this.setIconifiedByDefault(true);
    }

    public void setActivity(Activity activity) {
        this.activiy = activity;
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            activiy.getFragmentManager().popBackStack("searchFragment", 0);
        }
        return super.dispatchKeyEventPreIme(event);
    }
}
