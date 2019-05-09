package com.apps.szpansky.fiszkinainz.provider;

import com.apps.szpansky.fiszkinainz.Constant;
import com.apps.szpansky.fiszkinainz.data.Structure;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLoadingProvider implements LoadingProvider {

    @Override
    public void loadData(final CallBack callBack, int questionId) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.siteURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Api question = retrofit.create(Api.class);

        question.structure(questionId, "tajne").enqueue(new Callback<Structure>() {
            @Override
            public void onResponse(Call<Structure> call, Response<Structure> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body().getQuestion());
                }
            }

            @Override
            public void onFailure(Call<Structure> call, Throwable t) {
                callBack.onFailed(t);
            }
        });


    }

}
