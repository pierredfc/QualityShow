package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;

import videolibrary.street.quality.qualityshow.api.user.callbacks.CategoryCallback;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategoryListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiAdapter;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/26/15.
 */
public class FilmHelper {

    private final ApiAdapter apiAdapter;
    private final FilmRepository filmRepository;

    public FilmHelper(Context context) {
        this.apiAdapter = new ApiAdapter(context, ApiConstants.API_URL);
        this.filmRepository = this.apiAdapter.createRepository(FilmRepository.class);
    }

    public void addCategorie(Film film, Category category, CategoryListener listener){
        filmRepository.addCategory((int) film.getId(), category, new CategoryCallback.AddCategoryCallback(listener));
    }

    public void getCategpries(Film film, CategoryListener listener){
        filmRepository.getCategories((int) film.getId(), new CategoryCallback.GetCategoriesCallback(listener));
    }

    public void deleteCategorie(Film film, Category category, CategoryListener listener){
        filmRepository.deleteCategory((int) film.getId(), (int) category.getId(), new CategoryCallback.DeleteCategoryCallback(listener));
    }

}
