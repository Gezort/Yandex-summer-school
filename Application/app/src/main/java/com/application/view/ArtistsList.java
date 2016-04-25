package com.application.view;

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

import com.application.R;
import com.application.model.Artist;
import com.application.presenter.ArtistsListPresenter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

public class ArtistsList extends AppCompatActivity {

    private ArtistListAdapter mAdapter;
    private List<Artist> mArtists;
    private RecyclerView mRecycler;
    private View mProgress;
    private ArtistsListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);
        mProgress = findViewById(R.id.download_progress);
        mArtists = new ArrayList<>();
        mPresenter = new ArtistsListPresenter(this);
        initToolbar();
        mPresenter.downloadData();
        mAdapter = new ArtistListAdapter(mArtists);
        initRecycler();
    }

    public List<Artist> getArtists() {
        return mArtists;
    }

    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

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
            String title = artist.getName();
            String genres = TextUtils.join(", ", artist.getGenres());
            String songs = String.format("%d albums, %d tracks", artist.getAlbums(), artist.getTracks());
            holder.mHolderArtist = artist;
            holder.mName.setText(title);
            holder.mGenres.setText(genres);
            holder.mSongs.setText(songs);
            mPresenter.downloadImage(artist.getId(), holder.mImageView, artist.getBigIconUrl());
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

            @Override
            public void onClick(View v) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, ArtistPage.class);
                intent.putExtra(NAME, mHolderArtist.getName());
                intent.putExtra(TRACKS, String.format("albums: %d   tracks: %d", mHolderArtist.getAlbums(), mHolderArtist.getTracks()));
                intent.putExtra(GENRES, TextUtils.join(", ", mHolderArtist.getGenres()));
                intent.putExtra(BIO, mHolderArtist.getBiography());
                intent.putExtra(ICON_URL, mHolderArtist.getBigIconUrl());
                context.startActivity(intent);
            }
        }
    }
}