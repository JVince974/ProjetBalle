package com.example.vincent.projetballe.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Vincent on 11/10/2017.
 */

public class GameView extends View {

    private Paint paint;
    private int[] colors;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v("Width", "X=" + getWidth()); // Longueur max
        Log.v("Height", "Y=" + getHeight()); // Hauteur Max

        paint = new Paint();


        int radius = 100; // rayon de la balle
        int strokeWidth = 5; // epaisseur du trait


        canvas.drawOval(10, 10, 10, 10, paint);

    }
}
