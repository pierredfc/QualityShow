package videolibrary.street.quality.qualityshow.listeners;

import java.util.List;

import videolibrary.street.quality.qualityshow.responseModel.BeanItem;

public interface RequestListener {
    void onResponseReceived(List<BeanItem> response);
}
