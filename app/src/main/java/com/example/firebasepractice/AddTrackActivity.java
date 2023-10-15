package com.example.firebasepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasepractice.Adapters.TrackListAdapter;
import com.example.firebasepractice.Models.Track;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView tvArtistName;
    EditText etTrackName;
    SeekBar seekBarRating;
    Button addTrackBtn;
    ListView lvTrackList;

    DatabaseReference databaseTrackRef;

    List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        tvArtistName = (TextView) findViewById(R.id.tvTrackNameOfTheArtist);
        etTrackName = (EditText) findViewById(R.id.editTextTrackName);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        addTrackBtn = (Button) findViewById(R.id.addTrackBtn);
        lvTrackList = (ListView) findViewById(R.id.lvTracksList);

        Intent intent = getIntent();

        trackList = new ArrayList<>();

        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        tvArtistName.setText(name);

        assert id != null;
        databaseTrackRef = FirebaseDatabase.getInstance().getReference("track").child(id);

        addTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });

    }



    private void saveTrack(){
        String trackName = etTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(trackName)){
            String trackId = databaseTrackRef.push().getKey();

            Track track = new Track(trackId,trackName,rating);

            assert trackId != null;
            databaseTrackRef.child(trackId).setValue(track);

            Toast.makeText(AddTrackActivity.this,"Track saved successfully",Toast.LENGTH_LONG).show();
            etTrackName.setText("");

        }else{
            Toast.makeText(AddTrackActivity.this,"You should enter a valid track name",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTrackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackList.clear();

                for(DataSnapshot trackSnapShot : snapshot.getChildren()){
                    Track track = trackSnapShot.getValue(Track.class);

                    trackList.add(track);
                }

                TrackListAdapter trackListAdapter = new TrackListAdapter(AddTrackActivity.this, trackList);
                lvTrackList.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}