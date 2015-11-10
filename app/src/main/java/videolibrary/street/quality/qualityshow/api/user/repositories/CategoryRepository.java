package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/31/15.
 */
public class CategoryRepository extends ModelRepository<Category> {
    public CategoryRepository() {
        super(ApiConstants.CATEGORIE_MODEL_NAME, ApiConstants.CATEGORIE_API_NAME, Category.class);
    }
}
