package com.example.gaalifinder;

public class AudioModel {
    private final String title;
    private final int audioResId;

    public AudioModel(String title, int audioResId) {
        this.title = title;
        this.audioResId = audioResId;
    }


    public String getTitle() {
        return title;
    }

    public int getAudioResId() {
        return audioResId;
    }
}
