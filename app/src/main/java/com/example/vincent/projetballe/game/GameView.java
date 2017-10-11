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

    private int windowsWidth;
    private int windowsHeight;

    private int radius;

    private Paint paint = new Paint();
    private int[] colors;

//    colors[0] = Color.BLACK;
//    colors[1] = Color.BLUE;
//    colors[2] = Color.CYAN;
//    colors[3] = Color.DKGRAY;
//    colors[4] = Color.GREEN;
//    colors[5] = Color.GRAY;
//    colors[6] = Color.LTGRAY;
//    colors[7] = Color.MAGENTA;
//    colors[8] = Color.RED;
//    colors[9] = Color.YELLOW;
//    colors[10] = Color.WHITE;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // rayon de la balle varie selon la taille de l'écran
        radius = (int) (windowsWidth * 4.63 / 100);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);

    }

    private void init() {
        // récupérer la taille de l'écran
        windowsWidth = getWidth();
        windowsHeight = getHeight();
        Log.v("WindowsSize", "Width=" + windowsWidth); // Longueur max
        Log.v("WindowsSize", "Height=" + windowsHeight); // Hauteur Max
    }
}
