package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;

/**
 * Created by elerion on 10/31/15.
 */
public interface CategorieListener {

    void categorieIsAdded(Category category);
    void getCategorie(ArrayList<Category> categories);
    void categorieIsDeleted();

    void onError(Throwable t);

}
