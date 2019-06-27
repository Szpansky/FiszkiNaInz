package com.apps.szpansky.fiszkinainz.provider;

import androidx.annotation.CallSuper;

public class BaseLoadingProvider implements LoadingProvider {
    @CallSuper
    public void loadData(CallBack callBack, int questionId) {
        callBack.onStartLoading();
    }
}
