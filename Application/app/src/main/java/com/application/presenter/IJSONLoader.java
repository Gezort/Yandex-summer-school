package com.application.presenter;

import com.application.model.Artist;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by miron on 24.04.16.
 */
public interface IJSONLoader {
    @GET("mobilization-2016/artists.json")
    Observable<Artist[]> getJSON();
}
