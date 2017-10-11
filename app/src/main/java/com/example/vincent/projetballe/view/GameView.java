package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.vincent.projetballe.model.Ball;


public class GameView extends View {

    private Paint mPaint;

//    private int[] colors;
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
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // dessiner chaque balle
        for (Ball uneBalle : Ball.lesBalles) {
            mPaint.setColor(uneBalle.getColor());
            canvas.drawCircle(uneBalle.getX(), uneBalle.getY(), uneBalle.getRadius(), mPaint);
        }

        invalidate(); // rafraichir en permanence
    }


}
