package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.vincent.projetballe.model.GameData;
import com.example.vincent.projetballe.model.LesBalles.Balle;

/**
 * Cette classe dessine toutes les balles
 */
public class GameView extends View {

    private Paint mPaint; // pour dessiner

    public GameView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // dessiner la balle de l'utilisateur
        if (GameData.userBalle != null) {
            mPaint.setColor(GameData.userBalle.getColor());
            canvas.drawCircle(GameData.userBalle.getPosX(), GameData.userBalle.getPosY(), GameData.userBalle.getRadius(), mPaint);
        }
        // dessiner chaque balle ia
        if (GameData.listIABalles != null) {
            for (Balle uneBalle : GameData.listIABalles) {
                mPaint.setColor(uneBalle.getColor());
                canvas.drawCircle(uneBalle.getPosX(), uneBalle.getPosY(), uneBalle.getRadius(), mPaint);
            }
        }

        invalidate(); // redessiner en permanence
    }


}
