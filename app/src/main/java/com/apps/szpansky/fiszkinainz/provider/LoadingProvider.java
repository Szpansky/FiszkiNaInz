package com.apps.szpansky.fiszkinainz.provider;

import com.apps.szpansky.fiszkinainz.data.Question;

public interface LoadingProvider {
    void loadData(CallBack callBack, int questionId);

    interface CallBack{
        void onSuccessLoading(Question question);

        void onFailedLoading(Throwable t);

        void onStartLoading();

        void onFinallyLoading();
    }
}
