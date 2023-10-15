package com.example.firebasepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.firebasepractice.Adapters.ArtistListAdapter;
import com.example.firebasepractice.Models.Artist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artistName";
    public static final String ARTIST_ID = "artistId";

    EditText editTextArtistName;
    Button addArtistBtn;
    Spinner spinnerGenres;
    ListView lvArtistList;

    List<Artist> artistList;

    DatabaseReference databaseArtistRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextArtistName = (EditText) findViewById(R.id.editTextArtistName);
        addArtistBtn = (Button) findViewById(R.id.addArtistBtn);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenre);
        lvArtistList = (ListView) findViewById(R.id.lvArtistsList);

        artistList = new ArrayList<>();


        databaseArtistRef = FirebaseDatabase.getInstance().getReference("artists");

        addArtistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });

        lvArtistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artistList.get(i);

                Intent intent = new Intent(MainActivity.this, AddTrackActivity.class);

                intent.putExtra(ARTIST_NAME,artist.getName());
                intent.putExtra(ARTIST_ID,artist.getId());

                startActivity(intent);
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    public void addArtist(){
        String artistName = editTextArtistName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if(!TextUtils.isEmpty(artistName)){
            String id  = databaseArtistRef.push().getKey();

            Artist artist = new Artist(id, artistName, genre);

            assert id != null;
            databaseArtistRef.child(id).setValue(artist);

            Toast.makeText(MainActivity.this, "Artist added", Toast.LENGTH_LONG).show();

            editTextArtistName.setText("");

        }else{
            Toast.makeText(MainActivity.this, "You should enter a name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {

        super.onStart();

        databaseArtistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                artistList.clear();

                for(DataSnapshot artistSnapshot : snapshot.getChildren()){

                    Artist artist = artistSnapshot.getValue(Artist.class);

                    artistList.add(artist);
                }

                ArtistListAdapter adapter = new ArtistListAdapter(MainActivity.this, artistList);
                lvArtistList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}