package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;
import android.util.Log;

import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.remoting.adapters.Adapter;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.callbacks.CategorieCallback;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategorieListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;

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

    public void addCategorie(Film film, Category category, CategorieListener listener){
        filmRepository.addCategory((int) film.getId(), category, new CategorieCallback.AddCategoryCallback(listener));
    }

    public void getCategpries(Film film, CategorieListener listener){
        filmRepository.getCategories((int) film.getId(), new CategorieCallback.GetCategoriesCallback(listener));
    }

    public void deleteCategorie(Film film, Category category, CategorieListener listener){
        filmRepository.deleteCategory((int) film.getId(), (int) category.getId(), new CategorieCallback.DeleteCategoryCallback(listener));
    }

}
