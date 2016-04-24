package com.application;

/**
 * Created by miron on 16.04.16.
 */
public class Artist {
    public int id;
    public String name;
    public String[] genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public Cover cover;

    public class Cover {
        public String small;
        public String big;

        public Cover(String small, String big) {
            this.small = small;
            this.big = big;
        }
    }

    public Artist(int id, String name, String[] genres, int tracks, int albums, String link,
                  String description, String small, String big) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        cover = new Cover(small, big);
    }
}
