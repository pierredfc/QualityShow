package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;

/**
 * Created by elerion on 10/31/15.
 */
public interface CategoryListener {

    void categorieIsAdded(Category category);
    void getCategories(ArrayList<Category> categories);
    void categorieIsDeleted();

    void onError(Throwable t);

}
