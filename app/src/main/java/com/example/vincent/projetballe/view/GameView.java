package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.vincent.projetballe.model.GameModel;
import com.example.vincent.projetballe.model.LesBalles.Balle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

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
        if (UserBalle.getInstance() != null) {
            mPaint.setColor(UserBalle.getInstance().getColor());
            canvas.drawCircle(UserBalle.getInstance().getPosX(), UserBalle.getInstance().getPosY(), UserBalle.getInstance().getRadius(), mPaint);
        }
        // dessiner chaque balle ia
        for (Balle uneBalle : GameModel.listIABalles) {
            mPaint.setColor(uneBalle.getColor());
            canvas.drawCircle(uneBalle.getPosX(), uneBalle.getPosY(), uneBalle.getRadius(), mPaint);
        }

        invalidate(); // redessiner en permanence
    }


}
