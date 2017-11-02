package com.example.vincent.projetballe.bibliotheque;


import android.content.Context;
import android.media.MediaPlayer;

import com.example.vincent.projetballe.controller.GameActivity;
import com.example.vincent.projetballe.model.GameObject.lesBonus.Bonus;


public class MyMediaPlayer {

    private Context context;
    private int rawFile;
    private MediaPlayer mediaPlayer;
    private int currentPosition;

    public MyMediaPlayer(GameActivity context, int rawFile) {
        this.context = context;
        this.rawFile = rawFile;
    }


    public void start() {
        stop();
        mediaPlayer = MediaPlayer.create(context, rawFile);
        mediaPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        stop();
                    }
                }
        );
        mediaPlayer.start();
    }


    public void pause() {
        mediaPlayer.pause();
        currentPosition = mediaPlayer.getCurrentPosition();
    }

    public void resume() {
        mediaPlayer.seekTo(currentPosition);
        mediaPlayer.start();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
