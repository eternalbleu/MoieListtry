package de.hskl.imst.tim.movielist.adapter.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hskl.imst.tim.movielist.R;
import de.hskl.imst.tim.movielist.model.Movie;

/**
 * Created by timap on 01.07.2017.
 */

public class MovieListAdapter extends ArrayAdapter<Movie> {
    public MovieListAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie currentMovie = getItem(position);

        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.movie_listitem,parent, false);
        }

        ((TextView) view.findViewById(R.id.titleDetail)).setText(currentMovie.getTitle());
        TextView desc = (TextView) view.findViewById(R.id.description);

        // Checken ob Beschreibung vorhanden, sonst wird die TextView versteckt
        if(currentMovie.getDesc() == null) {
            desc.setVisibility(View.GONE);
        } else {
           desc.setVisibility(View.VISIBLE);
            desc.setText(currentMovie.getDesc());
        }
        return view;
    }
}
