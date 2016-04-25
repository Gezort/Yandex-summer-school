package com.application.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by miron on 16.04.16.
 */
public class Artist {
    @SerializedName("id") private int mId;
    @SerializedName("name") private String mName;
    @SerializedName("genres") private String[] mGenres;
    @SerializedName("tracks") private int mTracks;
    @SerializedName("albums") private int mAlbums;
    @SerializedName("link") private String mLink;
    @SerializedName("description") private String mBiography;
    @SerializedName("cover") private Cover mCover;

    private class Cover {
        @SerializedName("small") private String mSmall;
        @SerializedName("big") private String mBig;

        public Cover(String small, String big) {
            mSmall = small;
            mBig = big;
        }
    }

    public Artist(int id, String name, String[] genres, int tracks, int albums, String link,
                  String biography, String small, String big) {
        mId = id;
        mName = name;
        mGenres = genres;
        mTracks = tracks;
        mAlbums = albums;
        mLink = link;
        mBiography = biography;
        mCover = new Cover(small, big);
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String[] getGenres() {
        return mGenres;
    }

    public int getTracks() {
        return mTracks;
    }

    public int getAlbums() {
        return mAlbums;
    }

    public String getLink() {
        return mLink;
    }

    public String getBiography() {
        return mBiography;
    }

    public String getSmallIconUrl() {
        return mCover.mSmall;
    }

    public String getBigIconUrl() {
        return mCover.mBig;
    }
}
