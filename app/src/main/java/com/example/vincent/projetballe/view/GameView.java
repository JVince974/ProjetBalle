package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.vincent.projetballe.model.GameModel;
import com.example.vincent.projetballe.model.LesBalles.Balle;
import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

import java.util.ArrayList;

/**
 * Cette classe dessine toutes les balles
 */
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
        UserBalle userBalle = GameModel.getUserBalle();
        ArrayList<IABalle> listIABalles = GameModel.getIABalles();


        if (userBalle != null) {
            mPaint.setColor(userBalle.getColor());
            canvas.drawCircle(userBalle.getX(), userBalle.getY(), userBalle.getRadius(), mPaint);
        }
        // dessiner chaque balle
        for (Balle uneBalle : listIABalles) {
            mPaint.setColor(uneBalle.getColor());
            canvas.drawCircle(uneBalle.getX(), uneBalle.getY(), uneBalle.getRadius(), mPaint);
        }
        // redessiner en permanence
        invalidate();
    }


}
