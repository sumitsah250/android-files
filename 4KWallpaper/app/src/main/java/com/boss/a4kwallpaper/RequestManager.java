package com.boss.a4kwallpaper;

import android.content.Context;
import android.widget.Toast;

import com.boss.Listners.CuretedResponseListners;
import com.boss.Listners.SearchResponseListners;
import com.boss.models.CuretedApiResponse;
import com.boss.models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuretedResponseListners listners, String page)
    {
        CallWallpaperList callWallpaperList;
        callWallpaperList = retrofit.create(CallWallpaperList.class);
        if((page ==null) || page.isEmpty()){
            page="1";
        }

        Call<CuretedApiResponse> call = callWallpaperList.getWallpapers(page,"20");
        call.enqueue(new Callback<CuretedApiResponse>() {
            @Override
            public void onResponse(Call<CuretedApiResponse> call, Response<CuretedApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "an error occurred", Toast.LENGTH_SHORT).show();
                }
                listners.onFetch(response.body(),response.message());

            }

            @Override
            public void onFailure(Call<CuretedApiResponse> call, Throwable t) {
                listners.onError(t.getMessage());

            }
        });

    }

    public void SearchCuratedWallpapers(SearchResponseListners listners, String page,String query)
    {
        CallWallpaperListSearch callWallpaperListSearch;
        callWallpaperListSearch = retrofit.create(CallWallpaperListSearch.class);
        if((page ==null) || page.isEmpty()){
            page="1";
        }

        Call<SearchApiResponse> call = callWallpaperListSearch.getWallpapers(query,page,"20");
        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "an error occurred", Toast.LENGTH_SHORT).show();
                }
                listners.onFetch((SearchApiResponse) response.body(),response.message());

            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listners.onError(t.getMessage());

            }
        });

    }
    private interface CallWallpaperList{
        @Headers({
                "Acceptt: application/jason",
                "Authorization: A1JOQhLXpUmdrYJer8vQsCE4G3PgXsbcE2Secyd65EZjn5weaAKX5PVe "
        })
        @GET("curated/")
        Call<CuretedApiResponse>  getWallpapers(
            @Query("page") String page,
            @Query("per_page") String per_page
        );
    }
    private interface CallWallpaperListSearch{
        @Headers({
                "Acceptt: application/jason",
                "Authorization: A1JOQhLXpUmdrYJer8vQsCE4G3PgXsbcE2Secyd65EZjn5weaAKX5PVe "
        })
        @GET("search/")
        Call<SearchApiResponse>  getWallpapers(
                @Query("query") String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }
}
