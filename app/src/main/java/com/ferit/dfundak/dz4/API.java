package com.ferit.dfundak.dz4;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Dora on 27/04/2017.
 */

public interface API {
    @GET
    Call<SearchResults> getDetails(@Url String url);
    @GET
    Call<SearchResults> getRaw(@Url String uri);
}