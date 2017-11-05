package com.example.vincent.projetballe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.vincent.projetballe.controller.GameActivity;
import com.example.vincent.projetballe.model.GameObject.lesBalles.Balle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.CatchBalle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.EnnemyBalle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.ShieldBalle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.UserBalle;
import com.example.vincent.projetballe.model.GameObject.lesBonus.BonusMalus;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Cette classe dessine toutes les balles
 */
public class AnimateGameView extends View {

    private static final String TAG = "AnimateGameView";

    private GameActivity mGameActivity; // toutes les balles sont ici
    private Paint mPaint; // pour dessiner


    public AnimateGameView(Context context) {
        super(context);
        initialise();
    }


    public AnimateGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }


    public AnimateGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise();
    }


    private void initialise() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGameActivity != null) {
            UserBalle userBalle = mGameActivity.getUserBalle();
            ArrayList<EnnemyBalle> ennemyBalleArrayList = mGameActivity.getEnnemyBalleArrayList();
            CatchBalle catchBalle = mGameActivity.getCatchBalle();
            BonusMalus bonus = mGameActivity.getBonus();
            ArrayList<ShieldBalle> shieldBalleArrayList = mGameActivity.getShieldBalleArrayList();

            // dessiner la balle de l'utilisateur
            if (userBalle != null) {
                mPaint.setColor(userBalle.getColor());
                canvas.drawCircle(userBalle.getPosX(), userBalle.getPosY(), userBalle.getCurrentRadius(), mPaint);
            }

            // dessiner chaque balle ia
            if (ennemyBalleArrayList != null) {
                try {
                    for (Balle balle : ennemyBalleArrayList) {
                        mPaint.setColor(balle.getColor());
                        canvas.drawCircle(balle.getPosX(), balle.getPosY(), balle.getCurrentRadius(), mPaint);
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }

            // dessiner la balle a attraper
            if (catchBalle != null) {
                mPaint.setColor(catchBalle.getColor());
                canvas.drawCircle(catchBalle.getPosX(), catchBalle.getPosY(), catchBalle.getCurrentRadius(), mPaint);
            }

            // dessiner le bonus
            if (bonus != null) {
                mPaint.setColor(bonus.getColor());
                canvas.drawRect(bonus.getLeft(), bonus.getTop(), bonus.getRight(), bonus.getBottom(), mPaint);
            }

            // dessiner les balles protecteurs
            if (shieldBalleArrayList != null) {
                try {
                    for (Balle balle : shieldBalleArrayList) {
                        mPaint.setColor(balle.getColor());
                        canvas.drawCircle(balle.getPosX(), balle.getPosY(), balle.getCurrentRadius(), mPaint);
                    }
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Log.w(TAG, "onDraw: GameThread is null");
        }
        invalidate(); // redessiner en permanence
    }


    /**
     * Permet de récupérer tous les objets du jeu à dessiner,
     * ne pas oublier d'appeler cette méthode sinon rien ne se dessine
     */
    public void setGameActivity(GameActivity gameActivity) {
        mGameActivity = gameActivity;
    }
}
