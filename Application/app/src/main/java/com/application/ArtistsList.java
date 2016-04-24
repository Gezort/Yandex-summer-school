package com.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArtistsList extends AppCompatActivity {

    private final static String LOG_TAG = "Exception";

    private ArtistListAdapter mAdapter;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);
        initAdapter();
        initRecycler();
    }

    private void initAdapter() {
        List<Artist> listArtists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Artist artist = new Artist(1080505 + i, "Tove Lo", new String[]{"pop", "dance", "electronics"}, 81, 22,
                    "http://www.tove-lo.com/", "description", "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300",
                    "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000");
            listArtists.add(artist);
        }
        mAdapter = new ArtistListAdapter(listArtists);
    }

    private void initRecycler() {
        mRecycler = (RecyclerView) findViewById(R.id.artist_list_recycler);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
        private List<Artist> mArtists;

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
            String songs = String.format("albums: %d    tracks: %d", artist.albums, artist.tracks);
            holder.mHolderArtist = artist;
            holder.mTitle.setText(title);
            holder.mGenres.setText(genres);
            holder.mSongs.setText(songs);
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            Artist mHolderArtist;
            TextView mTitle;
            TextView mGenres;
            TextView mSongs;
            ImageView mImageView;

            ViewHolder(View item) {
                super(item);
                mTitle = (TextView) item.findViewById(R.id.card_title);
                mGenres = (TextView) item.findViewById(R.id.card_genres);
                mSongs = (TextView) item.findViewById(R.id.card_songs);
                mImageView = (ImageView) item.findViewById(R.id.artist_small_icon);
                item.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, ArtistPage.class);
                context.startActivity(intent);
            }
        }
    }
}