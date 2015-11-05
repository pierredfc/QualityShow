package videolibrary.street.quality.qualityshow.async;

import android.os.AsyncTask;

import java.util.List;

import videolibrary.street.quality.qualityshow.listeners.CalendarListener;
import videolibrary.street.quality.qualityshow.utils.Requests;


public class CalendarRequestAsyncTask extends AsyncTask<String, Integer, List<String>> {

    private final CalendarListener calendarListener;

    public CalendarRequestAsyncTask(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        return Requests.calendarSearch(params[0], Integer.parseInt(params[1]));
    }

    @Override
    protected void onPostExecute(List<String> response) {
        calendarListener.onCalendarRequestReceived(response);
    }
}
