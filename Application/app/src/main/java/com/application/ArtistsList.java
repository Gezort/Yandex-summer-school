package com.application;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ArtistsList extends AppCompatActivity {

    private ArtistListAdapter mAdapter;
    private List<Artist> mArtists;
    private RecyclerView mRecycler;
    private View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);
        mProgress = findViewById(R.id.download_progress);
        mArtists = new ArrayList<>();
        initToolbar();
        downloadData();
    }

    private void downloadData() {
        mProgress.setVisibility(View.VISIBLE);
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
        Toast.makeText(getApplicationContext(), "Error: can't download data", Toast.LENGTH_LONG);
    };

    private Action1<Artist> mOnNext = (artist) -> {
        mArtists.add(artist);
    };

    private Action0 onComplete = () -> {
        mProgress.setVisibility(View.GONE);
        mAdapter = new ArtistListAdapter(mArtists);
        initRecycler();
    };

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.artist_list_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.artist_list_header);
    }

    private void initRecycler() {
        mRecycler = (RecyclerView) findViewById(R.id.artist_list_recycler);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
        private List<Artist> mArtists;
        private DisplayImageOptions mDIOptions;

        public ArtistListAdapter(List<Artist> artists) {
            mArtists = artists;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.artist_card, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Artist artist = mArtists.get(position);
            String title = artist.name;
            String genres = TextUtils.join(", ", artist.genres);
            String songs = String.format("%d albums, %d tracks", artist.albums, artist.tracks);
            holder.mHolderArtist = artist;
            holder.mName.setText(title);
            holder.mGenres.setText(genres);
            holder.mSongs.setText(songs);
            holder.downloadImage();
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public static final String NAME = "NAME";
            public static final String TRACKS = "TRACKS";
            public static final String GENRES = "GENRES";
            public static final String BIO = "BIO";
            public static final String ICON_URL = "ICON_URL";

            Artist mHolderArtist;
            TextView mName;
            TextView mGenres;
            TextView mSongs;
            ImageView mImageView;

            ViewHolder(View item) {
                super(item);
                mName = (TextView) item.findViewById(R.id.card_title);
                mGenres = (TextView) item.findViewById(R.id.card_genres);
                mSongs = (TextView) item.findViewById(R.id.card_tracks);
                mImageView = (ImageView) item.findViewById(R.id.artist_small_icon);
                item.setOnClickListener(this);
            }

            void downloadImage() {
                Picasso.with(getApplicationContext())
                        .load(mHolderArtist.cover.small)
                        .into(mImageView);
            }

            @Override
            public void onClick(View v) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, ArtistPage.class);
                intent.putExtra(NAME, mHolderArtist.name);
                intent.putExtra(TRACKS, String.format("albums: %d   tracks: %d", mHolderArtist.albums, mHolderArtist.tracks));
                intent.putExtra(GENRES, TextUtils.join(", ", mHolderArtist.genres));
                intent.putExtra(BIO, mHolderArtist.description);
                intent.putExtra(ICON_URL, mHolderArtist.cover.big);
                context.startActivity(intent);
            }
        }
    }
}