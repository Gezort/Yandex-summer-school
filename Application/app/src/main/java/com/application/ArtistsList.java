package com.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        initToobar();
        initAdapter();
        initRecycler();
    }

    private void initToobar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.artist_list_toolbar);
//        setSupportActionBar(toolbar);
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
        private List<Artist> mArtists;

        public ArtistListAdapter(List<Artist> artists) {
            mArtists = artists;
            Log.e(LOG_TAG, Integer.toString(mArtists.size()));
            initRecycler();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.artist_card, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Log.e(LOG_TAG, Integer.toString(position) + " " + mArtists.get(position));
            Artist artist = mArtists.get(position);
            holder.mHolderArtist = artist;
            holder.mTitle.setText(artist.name);
            String genres = artist.genres[0];
            for (int i = 1; i < artist.genres.length; i++) {
                genres += ", " + artist.genres[i];
            }
            String songs = artist.tracks + " " + artist.albums;
            holder.mGenres.setText(genres);
            holder.mSongs.setText(songs);
            Log.e(LOG_TAG, artist.name + " " + genres + " " + songs);
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {

            Artist mHolderArtist;
            TextView mTitle;
            TextView mGenres;
            TextView mSongs;
            ImageView mImageView;

            ViewHolder(View item) {
                super(item);
                setContentView(R.layout.artist_card);
                mTitle = (TextView) findViewById(R.id.card_title);
                mGenres = (TextView) findViewById(R.id.card_genres);
                mSongs = (TextView) findViewById(R.id.card_songs);
                mImageView = (ImageView) findViewById(R.id.artist_small_icon);
//                item.setOnClickListener(this);
            }

//            @Override
//            public void onClick(View v) {
//                Log.e(LOG_TAG, "On click called");
//                Context context = itemView.getContext();
//                Intent intent = new Intent(context, ArtistPage.class);
//                context.startActivity(intent);
//            }
        }
    }
}
