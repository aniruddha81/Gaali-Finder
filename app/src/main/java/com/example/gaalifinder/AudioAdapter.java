package com.example.gaalifinder;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {

    private final List<AudioModel> audioList;
    private final Context context;


    public AudioAdapter(List<AudioModel> audioList, Context context) {
        this.audioList = audioList;
        this.context = context;
    }


    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        AudioModel audio = audioList.get(position);
        holder.audioTitle.setText(audio.getTitle());

        holder.playButton.setOnClickListener(v -> playAudio(audio.getAudioResId()));

        holder.shareButton.setOnClickListener(v -> shareAudio(audio.getAudioResId()));
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView audioTitle;
        ImageButton playButton, shareButton; // Use ImageButton instead of Button

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            audioTitle = itemView.findViewById(R.id.audioTitle);
            playButton = itemView.findViewById(R.id.playButton); // Cast to ImageButton
            shareButton = itemView.findViewById(R.id.shareButton); // Cast to ImageButton
        }
    }

    private void playAudio(int audioResId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, audioResId);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
    }

    private void shareAudio(int audioResId) {
        try {
            File cacheDir = new File(context.getCacheDir(), "audio");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            File audioFile = new File(cacheDir, "shared_audio.mp3");
            try (InputStream inputStream = context.getResources().openRawResource(audioResId);
                 FileOutputStream outputStream = new FileOutputStream(audioFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }

            Uri audioUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", audioFile);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("audio/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, audioUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share Audio"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to share audio file", Toast.LENGTH_SHORT).show();
        }
    }
}
