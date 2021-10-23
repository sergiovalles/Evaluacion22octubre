package com.example.practica22octubre2021.Models;

public class Movie {
    private String id_movie;
    private String title_movie;
    private String description_moview;
    private String url_picture_movie;
    private String autor_movie;

    public Movie() {
    }

    public Movie(String title_movie, String description_moview, String url_picture_movie, String autor_movie) {
        this.title_movie = title_movie;
        this.description_moview = description_moview;
        this.url_picture_movie = url_picture_movie;
        this.autor_movie = autor_movie;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getTitle_movie() {
        return title_movie;
    }

    public void setTitle_movie(String title_movie) {
        this.title_movie = title_movie;
    }

    public String getDescription_moview() {
        return description_moview;
    }

    public void setDescription_moview(String description_moview) {
        this.description_moview = description_moview;
    }

    public String getUrl_picture_movie() {
        return url_picture_movie;
    }

    public void setUrl_picture_movie(String url_picture_movie) {
        this.url_picture_movie = url_picture_movie;
    }

    public String getAutor_movie() {
        return autor_movie;
    }

    public void setAutor_movie(String autor_movie) {
        this.autor_movie = autor_movie;
    }
}
