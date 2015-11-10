package videolibrary.street.quality.qualityshow.listeners;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;

public interface RequestListener {
    void onResponseReceived(List<Object> response);
}
