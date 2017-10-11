package com.example.vincent.projetballe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends Activity {

    private View gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.GameView);
    }
}
