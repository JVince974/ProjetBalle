package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.vincent.projetballe.controller.GameActivity;
import com.example.vincent.projetballe.model.Ball;

/**
 * Created by Vincent on 11/10/2017.
 */

public class GameView extends View {

    public static int windowsWidth;
    public static int windowsHeight;

    private Paint paint;
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

        // dessiner chaque balle
        for (Ball uneBalle : GameActivity.lesBalles) {
            Log.v("uneBalle", "" + uneBalle.toString());
            canvas.drawCircle(uneBalle.getX(), uneBalle.getY(), uneBalle.getRadius(), paint);
        }

        invalidate(); // redessiner tout le temps
    }

    private void init() {
        // récupérer la taille de l'écran
        windowsWidth = getWidth();
        windowsHeight = getHeight();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Log.v("WindowsSize", "Width=" + windowsWidth); // Longueur max
        Log.v("WindowsSize", "Height=" + windowsHeight); // Hauteur Max
    }
}
