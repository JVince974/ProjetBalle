package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.vincent.projetballe.model.GameModel;
import com.example.vincent.projetballe.model.LesBalles.Balle;

/**
 * Cette classe dessine toutes les balles
 */
public class GameView extends View {

    // taille de la vue
    public static int viewWidth;
    public static int viewHeight;

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
        if (GameModel.userBalle != null) {
            mPaint.setColor(GameModel.userBalle.getColor());
            canvas.drawCircle(GameModel.userBalle.getPosX(), GameModel.userBalle.getPosY(), GameModel.userBalle.getRadius(), mPaint);
        }
        // dessiner chaque balle ia
        for (Balle uneBalle : GameModel.listIABalles) {
            mPaint.setColor(uneBalle.getColor());
            canvas.drawCircle(uneBalle.getPosX(), uneBalle.getPosY(), uneBalle.getRadius(), mPaint);
        }

        invalidate(); // redessiner en permanence
    }


}
