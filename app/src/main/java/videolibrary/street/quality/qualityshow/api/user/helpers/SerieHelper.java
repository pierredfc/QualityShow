package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;

import videolibrary.street.quality.qualityshow.api.user.callbacks.CategoryCallback;
import videolibrary.street.quality.qualityshow.api.user.callbacks.SaisonCallback;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategoryListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SaisonListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.SerieRepository;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiAdapter;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

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

    public void addCategorie(Serie serie, Category category, CategoryListener listener){
        serieRepository.addCategory((int) serie.getId(), category, new CategoryCallback.AddCategoryCallback(listener));
    }

    public void getCategories(Serie serie, CategoryListener listener){
        serieRepository.getCategories((int) serie.getId(), new CategoryCallback.GetCategoriesCallback(listener));
    }

    public void deleteCategory(Serie serie, Category category, CategoryListener listener){
        serieRepository.deleteCategory((int) serie.getId(), (int) category.getId(), new CategoryCallback.DeleteCategoryCallback(listener));
    }

    public void getSaisons(Serie serie, boolean episode, SaisonListener listener){
        serieRepository.getSaisons((int) serie.getId(), episode, new SaisonCallback.GetSaisonsCallback(listener));
    }

    public void addSaison(Serie serie, Saison saison, SaisonListener listener){
        serieRepository.addSaison((int) serie.getId(), saison, new SaisonCallback.AddSaisonCallback(listener));
    }

    public void deleteSaison(Serie serie, Saison saison, SaisonListener listener){
        serieRepository.deleteSaison((int) serie.getId(), (int) saison.getId(), new SaisonCallback.DeleteSaisonCallback(listener));
    }
}
