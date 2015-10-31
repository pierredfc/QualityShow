package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategoryListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.CategoryRepository;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/31/15.
 */
public class CategoryCallback {

    public static class GetCategoriesCallback extends Adapter.JsonArrayCallback{

        private CategoryListener listener;

        public GetCategoriesCallback(CategoryListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON array or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON array.
         */
        @Override
        public void onSuccess(JSONArray response) {
            ArrayList<Category> categories = new ArrayList<>();
            CategoryRepository repository = new CategoryRepository();
            if (response != null){
                for (int i = 0; i < response.length(); i++){
                    JSONObject object = null;

                    Category category = object != null
                            ? repository.createObject(JsonUtil.fromJson(object))
                            : null;
                    if(category != null)
                        categories.add(category);

                    try {
                        object = response.getJSONObject(i);
                    } catch (JSONException e) {
                        Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), e);
                        this.listener.onError(e);
                    }
                }
            }
            Log.d(Constants.Log.TAG, "Categories received");
            this.listener.getCategories(categories);

        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class AddCategoryCallback extends Adapter.JsonObjectCallback{

        private CategoryListener listener;

        public AddCategoryCallback(CategoryListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON object or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON object.
         */
        @Override
        public void onSuccess(JSONObject response) {
            Log.d(Constants.Log.TAG, "Category added");
            CategoryRepository repository = new CategoryRepository();
            Category category = response != null
                    ? repository.createObject(JsonUtil.fromJson(response))
                    : null;
            this.listener.categorieIsAdded(category);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class DeleteCategoryCallback implements Adapter.Callback{

        private CategoryListener listener;

        public DeleteCategoryCallback(CategoryListener categorieListener) {
            this.listener = categorieListener;
        }

        /**
         * The method invoked when the call completes successfully.
         *
         * @param response The HTTP response body.
         */
        @Override
        public void onSuccess(String response) {
            Log.d(Constants.Log.TAG, "Category deleted");
            this.listener.categorieIsDeleted();
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }
}
