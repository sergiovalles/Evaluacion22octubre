package com.example.practica22octubre2021.Library.Sqlite;

import static com.example.practica22octubre2021.Library.Utils.Variables.receivedMovieOffline;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.practica22octubre2021.Models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronsoft on 9/16/2017.
 */
public class MovieHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "moviesPracticaOf2.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "Movies";

    //Movie
    public static final String COLUMN_ID_MOVIE = "_id";
    public static final String COLUMN_TITLE_MOVIE = "title";
    public static final String COLUMN_DESCRIPTION_MOVIE = "description";
    public static final String COLUMN_AUTOR_MOVIE = "autor";
    public static final String COLUMN_URL_PICTURE_MOVIE = "picture";

    public MovieHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID_MOVIE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE_MOVIE + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION_MOVIE + " TEXT NOT NULL, " +
                COLUMN_URL_PICTURE_MOVIE + " TEXT NOT NULL, " +
                COLUMN_AUTOR_MOVIE + " TEXT NOT NULL);"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void saveNewMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE_MOVIE, movie.getTitle_movie());
        values.put(COLUMN_DESCRIPTION_MOVIE, movie.getDescription_moview());
        values.put(COLUMN_URL_PICTURE_MOVIE, movie.getUrl_picture_movie());
        values.put(COLUMN_AUTOR_MOVIE, movie.getAutor_movie());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int contadorMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int elementos = cursor.getCount();
        cursor.close();
        return elementos;
    }


    @SuppressLint("Range")
    public Movie getPerson(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);
        Movie receivedMovie = new Movie();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedMovie.setTitle_movie(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE_MOVIE)));
            receivedMovie.setDescription_moview(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_MOVIE)));
            receivedMovie.setUrl_picture_movie(cursor.getString(cursor.getColumnIndex(COLUMN_URL_PICTURE_MOVIE)));
            receivedMovie.setAutor_movie(cursor.getString(cursor.getColumnIndex(COLUMN_AUTOR_MOVIE)));
        }
        return receivedMovie;
    }

    /**obteniendo REIGSTRO QUALTIA**/
    @SuppressLint("Range")
    public void getIdMovie(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Movies";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Movie> receivedMovie22 = new ArrayList();
        Movie receivedMovie = new Movie();
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                receivedMovie.setTitle_movie(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE_MOVIE)));
                receivedMovie.setDescription_moview(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_MOVIE)));
                receivedMovie.setUrl_picture_movie(cursor.getString(cursor.getColumnIndex(COLUMN_URL_PICTURE_MOVIE)));
                receivedMovie.setAutor_movie(cursor.getString(cursor.getColumnIndex(COLUMN_AUTOR_MOVIE)));
                receivedMovieOffline.add(new Movie(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE_MOVIE))
                        ,cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_MOVIE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_URL_PICTURE_MOVIE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_AUTOR_MOVIE))));
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public void deleteMovie(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
