package de.hskl.imst.tim.movielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import de.hskl.imst.tim.movielist.adapter.listview.MovieListAdapter;
import de.hskl.imst.tim.movielist.database.MovieDatabase;
import de.hskl.imst.tim.movielist.model.Movie;

public class MovieList extends AppCompatActivity {

    private ListView listView;
    private List<Movie> dataSource;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Der folgende Code ist da damit man den eingegebenen Namen angezeigt bekommt in der App
        setContentView(R.layout.activity_movie_list);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message); // Um den Namen in Movielist an zu zeigen

        this.listView = (ListView) findViewById(R.id.movies);

        Button create = (Button) findViewById(R.id.create);
        Button clearAll = (Button) findViewById(R.id.clearall);

        this.dataSource = MovieDatabase.getInstance(this).readAllMovies();
        //dataSource.add(new Movie("Star Wars"));
        //dataSource.add(new Movie("Fluch der Karibik", "Piraten"));
        this.adapter = new MovieListAdapter(this, dataSource);

        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object element = parent.getAdapter().getItem(position);

                if (element instanceof Movie) {
                    Movie movie = (Movie) element;

                    Intent intent = new Intent(MovieList.this, MovieDetail.class);
                    intent.putExtra(MovieDetail.MOVIE_ID_KEY, movie.getId());
                    startActivity(intent);

                }
                Log.e("ClickOnList", element.toString());

            }
        });
        if (create != null) {
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDatabase db = MovieDatabase.getInstance(MovieList.this);

                    Intent intent = new Intent(MovieList.this, AddMovie.class);
                    startActivity(intent);

                    refreshListView();
                }
            });
        }

        if (clearAll != null) {
            clearAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDatabase db = MovieDatabase.getInstance(MovieList.this);
                    db.deleteAllMovies();
                    refreshListView();
                }
            });
        }
    }

    private void refreshListView() {
        dataSource.clear();
        dataSource.addAll(MovieDatabase.getInstance(this).readAllMovies());
        adapter.notifyDataSetChanged();
    }

}