package com.apps.szpansky.fiszkinainz.provider;

import com.apps.szpansky.fiszkinainz.data.Structure;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("get_question/?")
    Call<Structure> structure(@Query("question") int questionId, @Query("password") String password);
}
