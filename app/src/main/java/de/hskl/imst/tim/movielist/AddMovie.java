package de.hskl.imst.tim.movielist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import de.hskl.imst.tim.movielist.database.MovieDatabase;
import de.hskl.imst.tim.movielist.model.Movie;

public class AddMovie extends AppCompatActivity {

    private TextView titleText;
    private TextView descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        this.titleText = (TextView) findViewById(R.id.addMovTitle);
        this.descriptionText = (TextView) findViewById(R.id.addMovDesc);

        Button addButton = (Button) findViewById(R.id.addMovButton);
        Button cancelButton = (Button) findViewById(R.id.cancelAddMov);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String description = descriptionText.getText().toString();
                Movie mov = new Movie(title,description);
                MovieDatabase.getInstance(AddMovie.this).createMovie(mov);

                Context ctx = getApplicationContext();
                CharSequence text = "Film hinzugefügt";
                int duration = Toast.LENGTH_SHORT;
                Toast saveToast = Toast.makeText(ctx, text, duration);
                saveToast.show();

                Intent intent = new Intent(AddMovie.this, MovieList.class);
                startActivity(intent);
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMovie.this, MovieList.class);
                startActivity(intent);
            }
        });
    }
}
