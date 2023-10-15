package com.example.firebasepractice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasepractice.Models.Artist;
import com.example.firebasepractice.R;

import java.util.List;

public class ArtistListAdapter extends ArrayAdapter<Artist> {

    public Context context;
    public List<Artist> artistList;


    public ArtistListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ArtistListAdapter(@NonNull Context context, List<Artist> artistList) {
        super(context, R.layout.artist_list_layout, artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = LayoutInflater.from(context).inflate(R.layout.artist_list_layout, null, true);

        TextView tvArtistName = listViewItem.findViewById(R.id.tvArtistName);
        TextView tvArtistGenre = listViewItem.findViewById(R.id.tvArtistGenre);

        Artist artist = artistList.get(position);

        tvArtistName.setText(artist.getName());
        tvArtistGenre.setText(artist.getGenre());



        return listViewItem;

    }
}
