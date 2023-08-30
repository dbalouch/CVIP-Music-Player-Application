package com.dhanibux.musicplayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private final int[] songs = {R.raw.song1, R.raw.song2, R.raw.song3, R.raw.jhumka, R.raw.dreamgirl2};

    private ImageButton playPauseButton;
    private ListView songListView;

    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseButton = findViewById(R.id.playPauseButtonLarge);
        ImageButton nextButton = findViewById(R.id.nextButton);
        songListView = findViewById(R.id.songListView);

        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);

        playPauseButton.setOnClickListener(v -> togglePlayback());
        nextButton.setOnClickListener(v -> playNextSong());

        setupSongList();
    }

    private void togglePlayback() {
        if (isPlaying) {
            mediaPlayer.pause();
            playPauseButton.setImageResource(R.drawable.pause2);
        } else {
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.play);
        }
        isPlaying = !isPlaying;
    }

    private void playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % songs.length;
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        mediaPlayer.start();
    }

    private void setupSongList() {
        String[] songTitles = {"Song 1", "Song 2", "Song 3", "Jhumka", "Dream Girl"};

        ArrayAdapter<String> songListAdapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_song,
                R.id.songTitleTextView,
                songTitles
        );

        songListView.setAdapter(songListAdapter);

        songListView.setOnItemClickListener((parent, view, position, id) -> {
            currentSongIndex = position;
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(MainActivity.this, songs[currentSongIndex]);
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.pause2);
            isPlaying = true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
