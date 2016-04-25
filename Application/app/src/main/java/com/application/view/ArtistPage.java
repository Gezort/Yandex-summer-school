package com.application.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.R;
import com.squareup.picasso.Picasso;

public class ArtistPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_page);
        Intent intent = getIntent();

        initToolbar(intent.getStringExtra(ArtistsList.ArtistListAdapter.ViewHolder.NAME));

        initImage(intent.getStringExtra(ArtistsList.ArtistListAdapter.ViewHolder.ICON_URL));

        initInfo(intent.getStringExtra(ArtistsList.ArtistListAdapter.ViewHolder.TRACKS),
                intent.getStringExtra(ArtistsList.ArtistListAdapter.ViewHolder.GENRES));

        initBiography(intent.getStringExtra(ArtistsList.ArtistListAdapter.ViewHolder.BIO));
    }

    private void initToolbar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.artist_page_toolbar);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(android.support.design.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener((View view) -> onBackPressed());
    }

    private void initImage(String url) {
        ImageView imageView = (ImageView) findViewById(R.id.artist_big_icon);
        Picasso.with(getApplicationContext()).load(url).into(imageView);
    }

    private void initInfo(String tracks, String genres) {
        TextView textTracks = (TextView) findViewById(R.id.card_tracks);
        TextView textGenres = (TextView) findViewById(R.id.card_genres);
        textTracks.setText(tracks);
        textGenres.setText(genres);
    }

    private void initBiography(String biography) {
        TextView textView = (TextView) findViewById(R.id.card_biography);
        textView.setText(biography);
    }
}
