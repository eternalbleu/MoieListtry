package de.hskl.imst.tim.movielist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import de.hskl.imst.tim.movielist.model.Movie;

/**
 * Created by timap on 01.07.2017.
 */

public class MovieDatabase extends SQLiteOpenHelper {
    public static MovieDatabase INSTANCE = null; //Singleton

    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "movies";

    private static final String ID_COLUMN = "ID";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";

    private MovieDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public static MovieDatabase getInstance (Context ctx) {
        if(INSTANCE==null) {
            INSTANCE = new MovieDatabase(ctx);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE "+ TABLE_NAME + " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " + NAME_COLUMN + " TEXT NOT NULL, " + DESCRIPTION_COLUMN + " TEXT)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }
    public Movie createMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, movie.getTitle());
        values.put(DESCRIPTION_COLUMN, movie.getDesc() == null ? null : movie.getDesc());

        long newID = db.insert(TABLE_NAME, null, values);
        db.close();

        return readMovie(newID);
    }
    public Movie readMovie(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // SELECT id, name, description WHERE id =?
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID_COLUMN, NAME_COLUMN, DESCRIPTION_COLUMN}, ID_COLUMN + " =?", new String[]{String.valueOf(id)}, null, null, null);

        Movie movie = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            movie = new Movie(cursor.getString(cursor.getColumnIndex(NAME_COLUMN)));
            movie.setId(cursor.getLong(cursor.getColumnIndex(ID_COLUMN)));

            String desc = null;

            if(!cursor.isNull(cursor.getColumnIndex(DESCRIPTION_COLUMN))) {
                desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN));
            }
            movie.setDesc(desc);
        }

        db.close();
        return movie;
    }
    public List<Movie> readAllMovies () {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = readMovie(cursor.getLong(cursor.getColumnIndex(ID_COLUMN)));
                if(movie != null) {
                    movies.add(movie);
                }
            } while (cursor.moveToNext());
        }

        db.close();

        return movies;
    }

    public Movie updateMovie (Movie movie) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, movie.getTitle());
        values.put(DESCRIPTION_COLUMN, movie.getDesc() == null ? null : movie.getDesc());

        db.update(TABLE_NAME, values, ID_COLUMN + " =?", new String[] {String.valueOf(movie.getId())});
        db.close();

        return this.readMovie(movie.getId());
    }
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COLUMN + " =?", new String[]{String.valueOf(movie.getId())});
        db.close();
    }
    public void deleteAllMovies () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

}
