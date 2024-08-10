package com.example.gaalifinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AudioModel> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        audioList = new ArrayList<>();
        loadAudioFiles();

        AudioAdapter audioAdapter = new AudioAdapter(audioList,this);
        recyclerView.setAdapter(audioAdapter);

    }

    private void loadAudioFiles(){
        audioList.add(new AudioModel(getString(R.string.bara_choda),R.raw.ba_cho));
        audioList.add(new AudioModel(getString(R.string.bokachoda_khanki),R.raw.boka_khan));
        audioList.add(new AudioModel(getString(R.string.khankir_chele),R.raw.k_chele));
        audioList.add(new AudioModel(getString(R.string.bessha_maagi),R.raw.bes_mg));
        audioList.add(new AudioModel(getString(R.string.motherchod),R.raw.mc));
        audioList.add(new AudioModel(getString(R.string.banchod),R.raw.bc));
    }
}