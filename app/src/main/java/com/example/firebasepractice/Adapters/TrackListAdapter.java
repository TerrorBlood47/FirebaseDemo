package com.example.firebasepractice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.firebasepractice.Models.Track;
import com.example.firebasepractice.R;

import java.util.List;

public class TrackListAdapter extends ArrayAdapter<Track> {

    private Context context;
    private List<Track> trackList;

    public TrackListAdapter(Context context, List<Track> trackList) {
        super(context, R.layout.track_list_layout, trackList);
        this.context = context;
        this.trackList = trackList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(context).inflate(R.layout.track_list_layout, parent, false);
        }

        TextView tvTrackName = listViewItem.findViewById(R.id.tvTrackName);
        TextView tvTrackRating = listViewItem.findViewById(R.id.tvTrackRating);

        Track track = trackList.get(position);

        tvTrackName.setText(track.getName());
        tvTrackRating.setText(String.valueOf(track.getRating())); // Convert rating to String

        return listViewItem;
    }
}
