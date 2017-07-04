package de.hskl.imst.tim.movielist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import de.hskl.imst.tim.movielist.database.MovieDatabase;
import de.hskl.imst.tim.movielist.model.Movie;

public class MovieDetail extends AppCompatActivity {

    private TextView titleText;
    private TextView descriptionText;


    public static final String MOVIE_ID_KEY = "ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        long id = getIntent().getLongExtra(MOVIE_ID_KEY, 0);

        final Movie movie = MovieDatabase.getInstance(this).readMovie(id);

        this.titleText = (TextView) findViewById(R.id.editTitle);
        this.descriptionText = (TextView) findViewById(R.id.editDesc);

        titleText.setText(movie.getTitle());
        descriptionText.setText(movie.getDesc());

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button delete = (Button) findViewById(R.id.deleteMovie);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setTitle(titleText.getText().toString());
                movie.setDesc(descriptionText.getText().toString());
                MovieDatabase.getInstance(MovieDetail.this).updateMovie(movie);

                Context ctx = getApplicationContext();
                CharSequence text = "Änderungen gespeichert.";
                int duration = Toast.LENGTH_SHORT;
                Toast saveToast = Toast.makeText(ctx, text, duration);
                saveToast.show();

                Intent intent = new Intent(MovieDetail.this, MovieList.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetail.this, MovieList.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDatabase.getInstance(MovieDetail.this).deleteMovie(movie);

                Context ctx = getApplicationContext();
                CharSequence text = "Film gelöscht.";
                int duration = Toast.LENGTH_SHORT;
                Toast saveToast = Toast.makeText(ctx, text, duration);
                saveToast.show();

                Intent intent = new Intent(MovieDetail.this, MovieList.class);
                startActivity(intent);
            }
        });
    }

}
