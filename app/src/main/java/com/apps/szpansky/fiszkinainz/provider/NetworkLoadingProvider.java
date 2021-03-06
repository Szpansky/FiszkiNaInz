package com.apps.szpansky.fiszkinainz.provider;

import com.apps.szpansky.fiszkinainz.Constants;
import com.apps.szpansky.fiszkinainz.data.Structure;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLoadingProvider extends BaseLoadingProvider {

    private Api question;

    public NetworkLoadingProvider() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.siteURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        question = retrofit.create(Api.class);
    }

    @Override
    public void loadData(final CallBack callBack, int questionId) {
        super.loadData(callBack, questionId);

        question.structure(questionId, Constants.sitePassword).enqueue(new Callback<Structure>() {
            @Override
            public void onResponse(Call<Structure> call, Response<Structure> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccessLoading(response.body().getQuestion());
                    callBack.onFinallyLoading();
                }
            }

            @Override
            public void onFailure(Call<Structure> call, Throwable t) {
                callBack.onFailedLoading(t);
                callBack.onFinallyLoading();
            }
        });


    }

}
