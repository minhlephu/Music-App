package com.example.music.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.music.R;
import com.example.music.activity.Login;
import com.example.music.activity.MainActivity;
import com.example.music.constant.GlobalFuntion;
import com.example.music.databinding.FragmentAddSongBinding;
import com.example.music.databinding.FragmentFeedbackBinding;
import com.example.music.model.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSongFragment extends Fragment {
    private FragmentAddSongBinding mfragmentAddSongBinding;
    private static ArrayList<Song> mListSong = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://music-app-3f527-default-rtdb.firebaseio.com/");
    DatabaseReference addSong = database.getReference("/playlists/" + Login.currentUser.getPhone());

    DatabaseReference setSong = database.getReference("/playlists");
    final String URL_PATTERN = "/https?:\\/\\/(?:[-\\w]+\\.)?([-\\w]+)\\.\\w+(?:\\.\\w+)?\\/?.*/i";
    public AddSongFragment() {
        // Required empty public constructor
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mfragmentAddSongBinding = mfragmentAddSongBinding.inflate(inflater, container, false);

        addSong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Song song = dataSnapshot.getValue(Song.class);
                    if (song == null) {
                        return;
                    }
                    mListSong.add(song);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mfragmentAddSongBinding.tvAdd.setOnClickListener(v -> onClickAddSong());

        return mfragmentAddSongBinding.getRoot();
    }

    private void onClickAddSong() {
        MainActivity activity = (MainActivity) getActivity();
        Song song = new Song(10, mfragmentAddSongBinding.edtSongName.getText().toString(),
                mfragmentAddSongBinding.edtUrlImage.getText().toString(),
                mfragmentAddSongBinding.edtUrlSong.getText().toString(),
                mfragmentAddSongBinding.edtArtist.getText().toString(),
                false, true, 10, false);
        mListSong.add(song);

        setSong.child(Login.currentUser.getPhone()).setValue(mListSong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(activity, "Thanh cong", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, new PlaylistFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}