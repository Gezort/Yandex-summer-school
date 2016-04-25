package com.application.presenter;

import android.widget.ImageView;
import android.widget.Toast;

import com.application.model.Artist;
import com.application.view.ArtistsList;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by miron on 25.04.16.
 */
public class ArtistsListPresenter {
    private ArtistsList mView;

    public ArtistsListPresenter(ArtistsList view) {
        mView = view;
    }

    public void downloadData() {
        mView.showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://download.cdn.yandex.net/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IJSONLoader loader = retrofit.create(IJSONLoader.class);
        Observable<Artist[]> observable = loader.getJSON();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(artists -> Observable.from(artists))
                .subscribe(mOnNext, mOnError, onComplete);
    }

    private Action1<Throwable> mOnError = (exception) -> {
        mView.hideProgress();
        Toast.makeText(mView.getApplicationContext(), "Error: can't download data", Toast.LENGTH_LONG);
    };

    private Action1<Artist> mOnNext = (artist) -> {
        mView.getArtists().add(artist);
    };

    private Action0 onComplete = () -> {
        mView.hideProgress();
    };


    public void downloadImage(int id, ImageView view, String url) {
        Picasso.with(mView.getApplicationContext())
                .load(url)
                .into(view);
    }
}
