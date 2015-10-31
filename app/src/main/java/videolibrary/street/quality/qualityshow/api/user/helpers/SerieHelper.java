package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;

import videolibrary.street.quality.qualityshow.api.user.callbacks.CategoryCallback;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategorieListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.SerieRepository;

/**
 * Created by elerion on 10/31/15.
 */
public class SerieHelper  {

    ApiAdapter adapter;
    SerieRepository serieRepository;

    public SerieHelper(Context context) {
        this.adapter = new ApiAdapter(context, ApiConstants.API_URL);
        this.serieRepository = new SerieRepository();
    }

    public void addCategorie(Serie serie, Category category, CategorieListener listener){
        serieRepository.addCategory((int) serie.getId(), category, new CategoryCallback.AddCategoryCallback(listener));
    }

    public void getCategories(Serie serie, CategorieListener listener){
        serieRepository.getCategories((int) serie.getId(), new CategoryCallback.GetCategoriesCallback(listener));
    }

    public void deleteCategory(Serie serie, Category category, CategorieListener listener){
        serieRepository.deleteCategory((int) serie.getId(), (int) category.getId(), new CategoryCallback.DeleteCategoryCallback(listener));
    }
}
