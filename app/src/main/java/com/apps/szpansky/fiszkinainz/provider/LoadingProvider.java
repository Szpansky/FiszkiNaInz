package com.apps.szpansky.fiszkinainz.provider;

import com.apps.szpansky.fiszkinainz.data.Question;

public interface LoadingProvider {
    void loadData(CallBack callBack, int questionId);

    interface CallBack{
        void onSuccess(Question question);

        void onFailed(Throwable t);
    }
}
