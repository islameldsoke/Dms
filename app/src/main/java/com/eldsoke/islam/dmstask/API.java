package com.eldsoke.islam.dmstask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by islam on 1/3/2018.
 */

public interface API {

    @GET("users/square/repos")
    Call<List<Repositories>> getRepo(@Query("access_token") String accessToken,
                                     @Query("page") int page,
                                     @Query("per_page") int perPage);
}
